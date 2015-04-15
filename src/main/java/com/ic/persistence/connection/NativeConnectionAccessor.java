package com.ic.persistence.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author HTeunissen
 * 
 * @param <T>
 */
public interface NativeConnectionAccessor<T extends Connection> {

	/**
	 * 
	 * @param connection
	 * @return Native JDBC Connection which typically is dependent on a ConnectionPool
	 * @throws SQLException
	 */
	public T getNativeConnection(Connection connection) throws SQLException ;
}
