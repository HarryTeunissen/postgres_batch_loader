package com.ic.persistence.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class DefaultNativeConnectionAccessor<C extends Connection> implements NativeConnectionAccessor<C> {

	private Class<C> connectionType ;
	
	public DefaultNativeConnectionAccessor(Class<C> connectionType) {
		super();
		this.connectionType = connectionType;
	}


	@Override
	public C getNativeConnection(Connection connection) throws SQLException {
		return connection.unwrap(connectionType) ;
	}
	
	
}
