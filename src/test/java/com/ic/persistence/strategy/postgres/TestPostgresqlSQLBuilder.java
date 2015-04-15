package com.ic.persistence.strategy.postgres;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ic.persistence.mapping.Column;
import com.ic.persistence.mapping.MappedProperty;
import com.ic.persistence.mapping.ObjectMapper;
import com.ic.persistence.mapping.TableReference;
import com.ic.persistence.strategy.postgres.impl.PostgresqlSQLBuilder;

public class TestPostgresqlSQLBuilder {
	
	
	private PostgresqlSQLBuilder sqlBuilder ;
	
	
	@Before
	public void setUpSqlBuilder() {
		sqlBuilder = PostgresqlSQLBuilder.create(createObjectMapper()) ;
	}
	
	@Test
	public void testCreateSQL() {
		assertEquals("CREATE TEMP TABLE test_table_temp(LIKE test_schema.test_table EXCLUDING INDEXES) ON COMMIT DROP", (sqlBuilder.createTempTableSQL())) ;
	}
	
	@Test
	public void testCopySQL() {
		assertEquals("COPY test_table_temp(id_column,join_column,data_column) FROM STDIN DELIMITER ',' CSV",(sqlBuilder.createCopyTableSQL())) ;
	}
	
	private static String UPSERT_SQL = 
			"WITH upd AS(\n" +
			"UPDATE test_schema.test_table t\n" +
			"SET join_column=s.join_column,data_column=s.data_column\n" +
			"FROM test_table_temp s\n" +
			"WHERE t.join_column=s.join_column\n" +
			"RETURNING s.join_column\n" +
			")\n" +
			"INSERT INTO test_schema.test_table(join_column,data_column)\n" +
			"SELECT join_column,data_column\n" +
			"FROM test_table_temp s LEFT JOIN upd t USING(join_column)\n" +
			"WHERE t.join_column IS NULL\n\n" ;
	
	
	@Test
	public void testUpsertSQL() {
		assertEquals(UPSERT_SQL, sqlBuilder.createUpsertSQL()) ;
	}
	
	
	private ObjectMapper<?> createObjectMapper() {
	
		final Column idColumn = new Column("id_column") ;
		final Column joinColumn = new Column("join_column") ;
		final Column dataColumn = new Column("data_column") ;
		
		final List<Column> columns = new ArrayList<>() ;
		columns.add(idColumn) ;
		columns.add(joinColumn) ;
		columns.add(dataColumn) ;
		
		final List<Column> includedColumns = columns.subList(1, columns.size()) ;
		final TableReference tableRef = new TableReference("test_schema", "test_table");
		
		return new ObjectMapper<Object>() {

			@Override
			public Collection<MappedProperty<Object>> getMappedProperties() {
				return null;
			}

			@Override
			public TableReference getTableReference() {
				return tableRef ;
			}

			@Override
			public Column getJoinColumn() {
				return joinColumn ;
			}

			@Override
			public Collection<Column> getAllColumns() {
				return columns ;
			}

			@Override
			public Collection<Column> getIncludedColumns() {
				return includedColumns ;
			}
			
		} ;
		
	}

}
