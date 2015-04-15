package com.ic.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

public interface BatchUpdateStrategy<T> {

	/**
	 * Performs a batch update of valueItr into Persistence store
	 * @param connection
	 * @param values
	 * @throws SQLException
	 * @throws IOException
	 * 
	 */
	public void update(Connection connection, Iterator<T> valueItr)
			throws SQLException, IOException ;
	
}