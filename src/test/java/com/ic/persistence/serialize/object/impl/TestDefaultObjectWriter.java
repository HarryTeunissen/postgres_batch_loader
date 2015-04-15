package com.ic.persistence.serialize.object.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.ic.persistence.serialize.SerializationFormat;
import com.ic.persistence.serialize.SerializationFormatException;
import com.ic.persistence.serialize.object.ObjectWriter;
import com.ic.persistence.serialize.object.ObjectWriterService;

public class TestDefaultObjectWriter {

	private StringWriter writer;
	private ObjectWriter objectWriter;

	@Before
	public void setup() throws SerializationFormatException, IOException {
		SerializationFormat format = SerializationFormat.create()
				.setObjectSepartor("\n").setValueSepartor(",")
				.setEscapeQuotes(false).build();

		ObjectWriterService svc = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(ObjectWriterService.class).to(
						ObjectWriterServiceImpl.class);
			}

		}).getInstance(ObjectWriterService.class);

		writer = new StringWriter();
		objectWriter = svc.createObjectWriter(writer, format);
		objectWriter.startObject();
	}

	@Test
	public void testEmpty() {

	}

	@Test
	public void testStructure() {
		verify("");
	}

	@Test
	public void testNullString() throws IOException {
		objectWriter.write((String) null);
		verify("");
	}

	@Test
	public void testString() throws IOException {
		objectWriter.write("A");
		verify("\"A\"");
	}

	@Test
	public void testNumber() throws IOException {
		objectWriter.write(10);
		verify("10");
	}

	@Test
	public void testNullDate() throws IOException {
		objectWriter.write((Date) null);
		verify("");
	}

	@Test
	public void testNullBoolean() throws IOException {
		objectWriter.write((Boolean) null);
		verify("");
	}

	@Test
	public void testColumnStructure() throws IOException {
		for (int i = 0; i < 3; i++) {
			objectWriter.writeNull();
		}
		verify(",,");
	}

	@Test
	public void testObjectStructure() throws IOException {
		for (int i = 0; i < 3; i++) {
			objectWriter.writeNull();
		}
		objectWriter.endObject();
		verify(",,\n");
	}

	@Test
	public void testObjectStructure2() throws IOException {
		objectWriter.writeNull();
		objectWriter.endObject();
		objectWriter.startObject();
		objectWriter.writeNull();
		objectWriter.endObject();

		verify("\n\n");
	}

	@Test
	public void testEmptyObjectStructure() throws IOException {
		objectWriter.endObject();
		verify("");
	}

	private void verify(String expected) {
		assertEquals(expected, writer.toString());
	}

}
