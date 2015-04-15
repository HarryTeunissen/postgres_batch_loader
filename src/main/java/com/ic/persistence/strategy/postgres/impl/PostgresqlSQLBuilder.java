package com.ic.persistence.strategy.postgres.impl;

import java.util.Collection;

import com.ic.persistence.mapping.Column;
import com.ic.persistence.mapping.ObjectMapper;
import com.ic.persistence.mapping.TableReference;

/**
 * 
 * @author HTeunissen
 *
 */
public class PostgresqlSQLBuilder {

	public static PostgresqlSQLBuilder create(ObjectMapper<?> mapper) {
		return new PostgresqlSQLBuilder(mapper) ;
	}
	
	private final ObjectMapper<?> mapper;
	private final TableReference tableReference;
	private final TableReference tempReference;
	private final TableReference structureReference;

	private PostgresqlSQLBuilder(ObjectMapper<?> mapper) {
		this.mapper = mapper;

		this.tableReference = mapper.getTableReference();
		this.tempReference = this.tableReference.createTempReference();
		this.structureReference = tableReference;
	}

	/**
	 * 
	 * @return a Temporary Table Creation SQL Statement
	 */
	public String createTempTableSQL() {
		StringBuilder sb = new StringBuilder();

		sb.append("CREATE TEMP TABLE ");
		sb.append(tempReference.getName());
		sb.append("(");
		sb.append("LIKE");
		sb.append(" ");
		sb.append(structureReference.getQualifiedName());
		sb.append(" ");
		sb.append("EXCLUDING");
		sb.append(" ");
		sb.append("INDEXES");
		sb.append(")");
		sb.append(" ");
		sb.append("ON COMMIT DROP");

		return sb.toString();
	}

	/**
	 * 
	 * @return a Copy from Temporary Table to Base Table SQL statement
	 */
	public String createCopyTableSQL() {

		StringBuilder sb = new StringBuilder();
		sb.append("COPY");
		sb.append(" ");
		sb.append(tempReference.getName());
		sb.append("(");
		writeColumns(sb, mapper.getAllColumns());
		sb.append(")");
		sb.append(" ");
		sb.append("FROM STDIN");
		sb.append(" DELIMITER ','");
		sb.append(" CSV");

		return sb.toString();
	}
	
	/**
	 * 
	 * @return an UPSERT (Merge) SQL Statement.
	 * Statment Generates an Update followed by insert using ANTI-JOIN 
	 */
	public String createUpsertSQL() {

		StringBuilder sb = new StringBuilder();

		String resultTable = "upd";

		sb.append("WITH");
		sb.append(" ");
		sb.append(resultTable);
		sb.append(" ");
		sb.append("AS");
		sb.append("(");

		sb.append("\n");

		writeUpdateCmd(sb);

		sb.append("\n");
		sb.append(")");
		sb.append("\n");

		writeInsertCmd(sb, resultTable);
		sb.append("\n");

		return sb.toString();
	}


	/**
	 * 
	 * @param sb
	 * @param columns
	 */
	private void writeColumns(StringBuilder sb, Collection<Column> columns) {
		int i = 0;
		for (Column c : columns) {
			if (i++ > 0) {
				sb.append(",");
			}
			sb.append(c.getName());
		}
	}

	/**
	 * 
	 * @param sb
	 * @param targetAlias
	 * @param sourceAlias
	 * @param cols
	 */
	private void writeSetColumns(StringBuilder sb, String targetAlias,
			String sourceAlias, Collection<Column> cols) {
		int i = 0;
		for (Column c : cols) {
			if (i++ > 0) {
				sb.append(",");
			}
			sb.append(c.getName());
			sb.append("=");
			sb.append(sourceAlias);
			sb.append(".");
			sb.append(c.getName());
		}
	}

	/**
	 * 
	 * @param sb
	 * @param targetAlias
	 * @param sourceAlias
	 * @param col
	 */
	private void writeJoin(StringBuilder sb, String targetAlias,
			String sourceAlias, Column col) {

		sb.append(targetAlias);
		sb.append(".");
		sb.append(col.getName());
		sb.append("=");
		sb.append(sourceAlias);
		sb.append(".");
		sb.append(col.getName());
	}

	/**
	 * 
	 * @param sb
	 */
	private void writeUpdateCmd(StringBuilder sb) {

		String targetAlias = "t";
		String sourceAlias = "s";

		sb.append("UPDATE");
		sb.append(" ");
		sb.append(tableReference.getQualifiedName());
		sb.append(" ");
		sb.append(targetAlias);

		sb.append("\n");

		sb.append("SET");
		sb.append(" ");
		writeSetColumns(sb, targetAlias, sourceAlias,
				mapper.getIncludedColumns());

		sb.append("\n");

		sb.append("FROM");
		sb.append(" ");
		sb.append(tempReference.getName());
		sb.append(" ");
		sb.append(sourceAlias);

		sb.append("\n");

		sb.append("WHERE");
		sb.append(" ");
		writeJoin(sb, targetAlias, sourceAlias, mapper.getJoinColumn());

		sb.append("\n");

		sb.append("RETURNING");
		sb.append(" ");
		sb.append(sourceAlias);
		sb.append(".");
		sb.append(mapper.getJoinColumn().getName());

	}

	/**
	 * 
	 * @param sb
	 * @param resultTable
	 */
	private void writeInsertCmd(StringBuilder sb, String resultTable) {

		String sourceAlias = "s";
		String targetAlias = "t";

		sb.append("INSERT INTO");
		sb.append(" ");
		sb.append(tableReference.getQualifiedName());
		sb.append("(");
		writeColumns(sb, mapper.getIncludedColumns());
		sb.append(")");
		sb.append("\n");

		sb.append("SELECT");
		sb.append(" ");
		writeColumns(sb, mapper.getIncludedColumns());

		sb.append("\n");

		sb.append("FROM");
		sb.append(" ");
		sb.append(tempReference.getName());
		sb.append(" ");
		sb.append(sourceAlias);
		sb.append(" ");
		sb.append("LEFT JOIN");
		sb.append(" ");
		sb.append(resultTable);
		sb.append(" ");
		sb.append(targetAlias);
		sb.append(" ");
		sb.append("USING");
		sb.append("(");
		sb.append(mapper.getJoinColumn().getName());
		sb.append(")");

		sb.append("\n");

		sb.append("WHERE"); // ANTI-JOIN
		sb.append(" ");
		sb.append(targetAlias);
		sb.append(".");
		sb.append(mapper.getJoinColumn().getName());
		sb.append(" ");
		sb.append("IS NULL");

		sb.append("\n");

	}


}
