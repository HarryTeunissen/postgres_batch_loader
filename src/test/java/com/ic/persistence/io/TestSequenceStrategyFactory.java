package com.ic.persistence.io;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.ic.persistence.serialize.SequenceStrategyFactory;
import com.ic.persistence.serialize.impl.SequenceStrategyFactoryImpl;
import com.ic.persistence.serialize.object.ObjectWriter;
import com.ic.persistence.serialize.property.PropertyWriter;


public class TestSequenceStrategyFactory {

	private SequenceStrategyFactory factory ;
	
	@Before
	public void setupGenerator() {
		factory   = Guice.createInjector(new AbstractModule(){
			@Override
			protected void configure() {
				bind(SequenceStrategyFactory.class).to(SequenceStrategyFactoryImpl.class) ;
			}
		}).getInstance(SequenceStrategyFactory.class) ;

	}
	
	@Test
	public void testIntGenerator() throws Exception {
		testIntGenerator(Integer.class);
		testIntGenerator(int.class);
	}
	
	@Test
	public void testLongGenerator() throws Exception {
		testLongGenerator(Long.class);
		testLongGenerator(long.class);
	}
	
	private void testIntGenerator(Class<?> clz) throws Exception {
		PropertyWriter<Object> pw = factory.create(clz) ;
		ObjectWriter writer = mock(ObjectWriter.class) ;
		for(int i=0 ; i<20 ; i++) {
			pw.write(null, writer);
			verify(writer).write((Number) i);
		}
	}
	
	private void testLongGenerator(Class<?> clz) throws Exception {
		PropertyWriter<Object> pw = factory.create(clz) ;
		ObjectWriter writer = mock(ObjectWriter.class) ;
		for(long i=0 ; i<20 ; i++) {
			pw.write(null, writer);
			verify(writer).write((Number) i);
		}
	}
	
}
