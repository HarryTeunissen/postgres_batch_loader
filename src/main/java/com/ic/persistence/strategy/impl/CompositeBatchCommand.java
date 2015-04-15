package com.ic.persistence.strategy.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import com.ic.persistence.strategy.BatchCommand;
import com.ic.persistence.strategy.BatchCommandContext;

/**
 * 
 * @author HTeunissen
 *
 * @param <T>
 * @param <C>
 */
public class CompositeBatchCommand<T, C extends Connection> implements BatchCommand<T, C> {
	
	private final transient Collection<BatchCommand<T, C>> cmds ;
	
	public CompositeBatchCommand(Collection<BatchCommand<T, C>> cmds) {
		super();
		this.cmds = cmds;
	}

	@Override
	public void execute(C connection, BatchCommandContext<T> ctx)
			throws SQLException, IOException {

		for(BatchCommand<T, C> cmd : cmds) {
			cmd.execute(connection, ctx);
		}
		
	}
	
}
