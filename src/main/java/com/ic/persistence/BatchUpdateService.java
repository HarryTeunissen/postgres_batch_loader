package com.ic.persistence;

import org.postgresql.core.BaseConnection;

import com.ic.persistence.connection.NativeConnectionAccessor;

/**
 * BatchUpdateService creates a BatchUpdateStrategy 
 * which is much faster than batch updating using JDBC/Hibernate.
 * 
 * Currently the service leverages Hibernate Mapping. This can easily be modified by 
 * changing the ObjectMapperService
 * 
 * 
 * @author HTeunissen
 *
 */
public interface BatchUpdateService {

	/**
	 * Creates  BatchUpdateStrategy<T>. 
	 * This BatchUpdateStrategy should be cached by caller
	 * @param clz
	 * @param nativeAccessor
	 * @return a BatchUpdateStrategy<T> for given persistence class
	 */
	public <T> BatchUpdateStrategy<T> createPostgreSQLStrategy(Class<T> clz,
			NativeConnectionAccessor<BaseConnection> nativeAccessor) throws BatchStrategyExcception ;

	/**
	 * 
	 * @param clz
	 * @return
	 * @throws BatchStrategyExcception
	 */
	public <T> BatchUpdateStrategy<T> createPostgreSQLStrategy(Class<T> clz) throws BatchStrategyExcception ;

	
}