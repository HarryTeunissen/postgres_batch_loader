package com.ic.persistence.strategy.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ic.persistence.BatchUpdateStrategy;
import com.ic.persistence.connection.NativeConnectionAccessor;
import com.ic.persistence.strategy.BatchCommand;
import com.ic.persistence.strategy.BatchCommandContext;

/**
 * 
 * @author HTeunissen
 *
 * PostgresSQL Batch Update Strategy
 * @param <T>
 */
public class DefaultBatchUpdateStrategy<T, C extends Connection> implements BatchUpdateStrategy<T> {

	private static Logger log = LoggerFactory
			.getLogger(DefaultBatchUpdateStrategy.class.getName());

	private NativeConnectionAccessor<C> connectionAccessor;
	private BatchCommand<T, C> batchCommand ;
	
	public DefaultBatchUpdateStrategy(
			NativeConnectionAccessor<C> connectionAccessor,
			BatchCommand<T, C> batchCommand) {
		super();
		this.connectionAccessor = connectionAccessor;
		this.batchCommand = batchCommand;
	}


	@Override
	public void update(final Connection connection, final Iterator<T> itr)
			throws SQLException, IOException {
		
		C nativeConnection = connectionAccessor.getNativeConnection(connection) ;
		
		boolean orginalAutoCommit = nativeConnection.getAutoCommit();
		try {
			nativeConnection.setAutoCommit(false);

			batchCommand.execute(nativeConnection, new BatchCommandContext<T>() {
				@Override
				public Iterator<T> iterator() {
					return itr;
				}
			});

			nativeConnection.commit();

		} catch (SQLException | IOException e) {
			try {
				nativeConnection.rollback();
			} catch (SQLException e1) {
				log.error(e1.getMessage(), e1);
			}

			throw e;

		} finally {
			try {
				nativeConnection.setAutoCommit(orginalAutoCommit);
			} catch (Exception e) {
				log.error(e.getMessage(), orginalAutoCommit);
			}
		}		
	}

	

}
