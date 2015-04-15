package com.ic.persistence.strategy.postgres.impl;

import org.postgresql.core.BaseConnection;

import com.ic.persistence.mapping.ObjectMapper;
import com.ic.persistence.pipe.IOPipeService;
import com.ic.persistence.serialize.ObjectSerializerService;
import com.ic.persistence.strategy.BatchCommand;
import com.ic.persistence.strategy.impl.UpdateBatchCommand;
import com.ic.persistence.strategy.postgres.PostgressCommandBuilder;

/**
 * 
 * @author HTeunissen
 * 
 * @param <T>
 */
class DefaultPostgressCommandBuilder<T> implements PostgressCommandBuilder<T> {

	private final transient PostgresqlSQLBuilder sqlBuilder;
	private final transient ObjectMapper<T> mapper;
	private final transient IOPipeService ioPipeService;
	private final transient ObjectSerializerService serializerService;

	DefaultPostgressCommandBuilder(IOPipeService ioPipeService,
			ObjectSerializerService serializerService, ObjectMapper<T> mapper) {
		this.ioPipeService = ioPipeService;
		this.serializerService = serializerService;
		this.mapper = mapper;
		sqlBuilder = PostgresqlSQLBuilder.create(mapper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.strategy.postgres.PostgressCommandBuilder#
	 * createTempTableCmd()
	 */
	public BatchCommand<T, BaseConnection> createTempTableCmd() {
		return new UpdateBatchCommand<T, BaseConnection>(
				sqlBuilder.createTempTableSQL());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.strategy.postgres.PostgressCommandBuilder#
	 * createCopyTableCommand()
	 */
	public BatchCommand<T, BaseConnection> createCopyTableCommand() {
		return new CopyCommand<>(ioPipeService, serializerService,
				sqlBuilder.createCopyTableSQL(), mapper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.strategy.postgres.PostgressCommandBuilder#
	 * createUpsertTable()
	 */
	public BatchCommand<T, BaseConnection> createUpsertTable() {
		return new UpdateBatchCommand<T, BaseConnection>(
				sqlBuilder.createUpsertSQL());
	}

}
