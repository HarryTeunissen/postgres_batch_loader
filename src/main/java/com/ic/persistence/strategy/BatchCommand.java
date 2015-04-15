package com.ic.persistence.strategy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface BatchCommand<T, C extends Connection> {
	
	/**
	 * Execute a BatchCommand based on a native connection C and a BatchCommandContext
	 * 
	 * @param connection
	 * @param ctx
	 * @throws SQLException
	 * @throws IOException
	 */
	public void execute(C connection, BatchCommandContext<T> ctx) throws SQLException, IOException ;
	
}
