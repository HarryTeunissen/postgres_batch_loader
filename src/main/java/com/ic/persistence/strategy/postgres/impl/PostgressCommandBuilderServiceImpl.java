package com.ic.persistence.strategy.postgres.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ic.persistence.mapping.ObjectMapper;
import com.ic.persistence.pipe.IOPipeService;
import com.ic.persistence.serialize.ObjectSerializerService;
import com.ic.persistence.strategy.postgres.PostgressCommandBuilder;
import com.ic.persistence.strategy.postgres.PostgressCommandBuilderService;

/**
 * 
 * @author HTeunissen
 *
 */
@Singleton
public class PostgressCommandBuilderServiceImpl implements
		PostgressCommandBuilderService {

	private IOPipeService ioPipeService;
	private ObjectSerializerService objectSerializerService;

	@Inject
	public PostgressCommandBuilderServiceImpl(IOPipeService ioPipeService,
			ObjectSerializerService objectSerializerService) {
		super();
		this.ioPipeService = ioPipeService;
		this.objectSerializerService = objectSerializerService;
	}

	@Override
	public <T> PostgressCommandBuilder<T> createCommandBuilder(
			ObjectMapper<T> mapper) {
		return new DefaultPostgressCommandBuilder<T>(ioPipeService,
				objectSerializerService, mapper);
	}

}
