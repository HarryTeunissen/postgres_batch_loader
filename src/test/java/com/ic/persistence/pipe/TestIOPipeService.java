package com.ic.persistence.pipe;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.ic.persistence.pipe.impl.IOPipeServiceImpl;
import com.ic.persistence.serialize.ObjectSerializer;

public class TestIOPipeService {

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

	@Test
	public void test() throws IOException {
		test("") ;
	}
	
	@Test
	public void test1() throws IOException {
		test("A") ;
	}
	
	@Test
	public void test2() throws IOException {
		test("AAAAAAAAAAAAAAAAAAAAAA") ;
	}
	
	
	
	//
	//
	//

	private static class TestSerializer<T> implements ObjectSerializer<T> {

		private Writer writer;

		public TestSerializer(Writer writer) {
			super();
			this.writer = writer;
		}

		@Override
		public void write(T value) throws Exception {
			writer.write(value.toString());
		}

		@Override
		public void flush() throws IOException {
			writer.flush();
		}

		@Override
		public void close() throws IOException {
			writer.close();
		}

	}
		
	
	private void test(String source) throws IOException {
		try(Reader reader = createReader(PipeUtils_Test.createStringIterator(source))) {
			String d = PipeUtils_Test.slurpReader(reader, 5) ;
			Assert.assertEquals(d, source);
		}
	}

	
	private <T> Reader createReader(Iterator<T> itr) throws IOException {
		return svc.createReader(itr, new ObjectSerializerFactory<T>() {
			@Override
			public ObjectSerializer<T> create(Writer writer) {
				return new TestSerializer<>(writer);
			}

		});
	}
	
	

}
