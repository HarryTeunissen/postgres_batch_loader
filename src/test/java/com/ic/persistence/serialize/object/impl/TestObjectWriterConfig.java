package com.ic.persistence.serialize.object.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.ic.persistence.serialize.SerializationFormat;
import com.ic.persistence.serialize.SerializationFormatException;
import com.ic.persistence.serialize.object.ObjectWriter;
import com.ic.persistence.serialize.object.ObjectWriterService;

public class TestObjectWriterConfig {

	private StringWriter writer;
	private ObjectWriterService svc ;
	
	@Before
	public void setup() {
		writer = new StringWriter();
		svc = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(ObjectWriterService.class).to(
						ObjectWriterServiceImpl.class);
			}

		}).getInstance(ObjectWriterService.class);

	}

	@Test
	public void testEscape() throws IOException {
		create(
				SerializationFormat.create().setEscapeQuotes(true)
						.setQuoteChar('"')).write("\"");
		verify("\"\"\"\"");
	}

	@Test
	public void testDateFormat() throws IOException, SerializationFormatException {
		String format = "yyyy-MM-dd HH:mm:ss";
		Date date = new Date();
		String expected = new SimpleDateFormat(format).format(date);
		create(SerializationFormat.create().setDateFormat(format)).write(date);
		verify(expected);
	}
	
	@Test
	public void testDateFormat2() throws IOException, SerializationFormatException {
		String format = "yyyy-MM-dd";
		Date date = new Date();
		String expected = new SimpleDateFormat(format).format(date);
		create(SerializationFormat.create().setDateFormat(format)).write(date);
		verify(expected);
	}

	@Test
	public void testColumnSeperator() throws IOException, SerializationFormatException {
		ObjectWriter w = create(SerializationFormat.create().setValueSepartor(
				"|"));
		w.writeNull();
		w.writeNull();
		verify("|");
	}

	@Test
	public void testNullValue() throws IOException, SerializationFormatException {
		ObjectWriter w = create(SerializationFormat.create()
				.setNullValue("NULL").setValueSepartor(","));
		w.writeNull();
		verify("NULL");
	}

	private ObjectWriter create(SerializationFormat.Builder fb) {
		return svc.createObjectWriter(writer, fb.build());
	}

	private void verify(String expected) {
		assertEquals(expected, writer.toString());
	}

}
