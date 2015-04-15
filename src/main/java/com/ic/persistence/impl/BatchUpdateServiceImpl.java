package com.ic.persistence.impl;

import org.postgresql.core.BaseConnection;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ic.persistence.BatchStrategyExcception;
import com.ic.persistence.BatchUpdateService;
import com.ic.persistence.BatchUpdateStrategy;
import com.ic.persistence.connection.DefaultNativeConnectionAccessor;
import com.ic.persistence.connection.NativeConnectionAccessor;
import com.ic.persistence.mapping.ObjectMapperService;
import com.ic.persistence.mapping.ObjectMappingException;
import com.ic.persistence.strategy.impl.BatchUpdateStrategyBuilder;
import com.ic.persistence.strategy.postgres.PostgressCommandBuilder;
import com.ic.persistence.strategy.postgres.PostgressCommandBuilderService;

/**
 * 
 * @author HTeunissen
 * 
 */
@Singleton
public class BatchUpdateServiceImpl implements BatchUpdateService {

	private final ObjectMapperService mappingService;
	private final PostgressCommandBuilderService commandService;

	@Inject
	public BatchUpdateServiceImpl(ObjectMapperService mappingService,
			PostgressCommandBuilderService commandFactory) {
		super();
		this.mappingService = mappingService;
		this.commandService = commandFactory;
	}

	@Override
	public <T> BatchUpdateStrategy<T> createPostgreSQLStrategy(Class<T> clz,
			NativeConnectionAccessor<BaseConnection> nativeAccessor)
			throws ObjectMappingException {

		PostgressCommandBuilder<T> cmdBuilder = commandService
				.createCommandBuilder(mappingService.bindObject(clz).build());

		return BatchUpdateStrategyBuilder.create(clz, nativeAccessor)
				.add(cmdBuilder.createTempTableCmd())
				.add(cmdBuilder.createCopyTableCommand())
				.add(cmdBuilder.createUpsertTable()).build();
	}

	@Override
	public <T> BatchUpdateStrategy<T> createPostgreSQLStrategy(Class<T> clz)
			throws BatchStrategyExcception {
		return createPostgreSQLStrategy(clz,
				new DefaultNativeConnectionAccessor<>(BaseConnection.class));
	}

}
