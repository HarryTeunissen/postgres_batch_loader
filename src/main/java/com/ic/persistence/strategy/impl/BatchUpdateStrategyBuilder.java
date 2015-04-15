package com.ic.persistence.strategy.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.ic.persistence.BatchUpdateStrategy;
import com.ic.persistence.connection.NativeConnectionAccessor;
import com.ic.persistence.strategy.BatchCommand;

/**
 * 
 * @author HTeunissen
 *
 * @param <T>
 * @param <C>
 */
public class BatchUpdateStrategyBuilder<T, C extends Connection> {

	public static <T, C extends Connection> BatchUpdateStrategyBuilder<T, C> create(Class<T> clz, NativeConnectionAccessor<C> connectionAccessor) {
		return new BatchUpdateStrategyBuilder<>(connectionAccessor) ;
	}
	
	private final transient NativeConnectionAccessor<C> connectionAccessor ;
	private final transient List<BatchCommand<T, C>> cmds = new ArrayList<>() ;
	
	public BatchUpdateStrategyBuilder(
			NativeConnectionAccessor<C> connectionAccessor) {
		super();
		this.connectionAccessor = connectionAccessor;
	}

	public BatchUpdateStrategyBuilder<T, C> add(BatchCommand<T, C> cmd) {
		cmds.add(cmd) ;
		return this ;
	}
	
	public BatchUpdateStrategy<T> build() {
		return new DefaultBatchUpdateStrategy<T, C>(connectionAccessor, new CompositeBatchCommand<>(cmds)) ;
	}
}
