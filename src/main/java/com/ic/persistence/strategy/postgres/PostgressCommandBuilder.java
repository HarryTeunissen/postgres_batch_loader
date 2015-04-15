package com.ic.persistence.strategy.postgres;

import org.postgresql.core.BaseConnection;

import com.ic.persistence.strategy.BatchCommand;

/**
 * 
 * @author HTeunissen
 *
 * @param <T>
 */
public interface PostgressCommandBuilder<T> {

	/**
	 * 
	 * @return BatchCommand BatchCommand<T, BaseConnection>
	 */
	public BatchCommand<T, BaseConnection> createTempTableCmd();

	public BatchCommand<T, BaseConnection> createCopyTableCommand();

	public BatchCommand<T, BaseConnection> createUpsertTable();

}