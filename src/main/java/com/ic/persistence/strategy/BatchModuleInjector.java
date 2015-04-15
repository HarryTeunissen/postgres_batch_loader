package com.ic.persistence.strategy;

import com.google.inject.AbstractModule;
import com.ic.persistence.BatchUpdateService;
import com.ic.persistence.impl.BatchUpdateServiceImpl;
import com.ic.persistence.mapping.ObjectMapperService;
import com.ic.persistence.mapping.impl.ObjectMapperServiceImpl;
import com.ic.persistence.pipe.IOPipeService;
import com.ic.persistence.pipe.impl.IOPipeServiceImpl;
import com.ic.persistence.serialize.ObjectSerializerService;
import com.ic.persistence.serialize.SequenceStrategyFactory;
import com.ic.persistence.serialize.impl.ObjectSerializerServiceImpl;
import com.ic.persistence.serialize.impl.SequenceStrategyFactoryImpl;
import com.ic.persistence.serialize.object.ObjectWriterService;
import com.ic.persistence.serialize.property.PropertyWriterService;
import com.ic.persistence.serialize.property.impl.PropertyWriterServiceImpl;
import com.ic.persistence.strategy.postgres.PostgressCommandBuilderService;
import com.ic.persistence.strategy.postgres.impl.PostgressCommandBuilderServiceImpl;

public class BatchModuleInjector extends AbstractModule {

	@Override
	protected void configure() {
		bind(ObjectMapperService.class).to(ObjectMapperServiceImpl.class) ;
		bind(IOPipeService.class).to(IOPipeServiceImpl.class) ;
		bind(SequenceStrategyFactory.class).to(SequenceStrategyFactoryImpl.class) ;
		bind(ObjectSerializerService.class).to(ObjectSerializerServiceImpl.class) ;
		bind(ObjectWriterService.class).to(ObjectWriterService.class) ;
		bind(PropertyWriterService.class).to(PropertyWriterServiceImpl.class) ;
		bind(PostgressCommandBuilderService.class).to(PostgressCommandBuilderServiceImpl.class) ;
		bind(BatchUpdateService.class).to(BatchUpdateServiceImpl.class) ;
	}
	
}
