package com.ic.persistence.strategy.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.ic.persistence.strategy.BatchCommand;
import com.ic.persistence.strategy.BatchCommandContext;

/**
 * 
 * @author HTeunissen
 *
 * @param <T>
 * @param <C>
 */
public class UpdateBatchCommand<T, C extends Connection> implements
		BatchCommand<T, C> {

	private final transient String sql;

	public UpdateBatchCommand(String sql) {
		super();
		this.sql = sql;
	}

	@Override
	public void execute(C connection, BatchCommandContext<T> ctx)
			throws SQLException, IOException {
		try (Statement stm = connection.createStatement()) {
			stm.executeUpdate(sql);
		}
	}

}
