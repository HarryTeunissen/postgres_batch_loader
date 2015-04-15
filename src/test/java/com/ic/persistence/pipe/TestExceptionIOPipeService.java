package com.ic.persistence.pipe;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.ic.persistence.pipe.impl.IOPipeServiceImpl;
import com.ic.persistence.serialize.ObjectSerializer;

public class TestExceptionIOPipeService {

	private IOPipeService svc;

	@Before
	public void setup() {
		svc = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(IOPipeService.class).to(IOPipeServiceImpl.class);
			}

		}).getInstance(IOPipeService.class);

	}

	@Test(expected = IOException.class)
	public void testIOExeption() throws IOException {
		test(new AbstractSerializer<String>() {
			@Override
			public void write(String value) throws Exception {
				throw new IOException();
			}
		});
	}

	@Test(expected = IOException.class)
	public void testRuntimeExeption() throws IOException {
		test(new AbstractSerializer<String>() {
			@Override
			public void write(String value) throws Exception {
				throw new RuntimeException();
			}
		});
	}
	
	

	private abstract static class AbstractSerializer<T> implements
			ObjectSerializer<T> {

		@Override
		public void flush() throws IOException {
		}

		@Override
		public void close() throws IOException {
		}

	}

	private void test(final ObjectSerializer<String> serializer)
			throws IOException {
		try (Reader reader = svc.createReader(PipeUtils_Test.createStringIterator("AA"),
				new ObjectSerializerFactory<String>() {
					@Override
					public ObjectSerializer<String> create(Writer writer) {
						return serializer;
					}

				})) {

			PipeUtils_Test.slurpReader(reader, 1);

		}
	}

}
