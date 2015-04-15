package com.ic.persistence.serialize.object.escape;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

public class TestEscapeStrategy {

	private StringWriter writer = new StringWriter();
	private TextEscapeStrategy strategy;

	@Before
	public void setup() {
		writer = new StringWriter();
		strategy = new QuoteEscapeStrategy('"') ;
	}

	@Test
	public void testString() throws IOException {
		strategy.escape(writer, "Test");
		verify("Test");
	}

	@Test
	public void testEsacpeString() throws IOException {
		strategy.escape(writer, "\"");
		verify(("\"\""));
	}

	@Test
	public void testNullString() throws IOException {
		strategy.escape(writer, null);
		verify("");
	}

	@Test
	public void testEmptyString() throws IOException {
		strategy.escape(writer, "");
		verify("");
	}

	@Test
	public void testEsacpe2String() throws IOException {
		strategy.escape(writer, "Test\"");
		verify("Test\"\"");
	}

	@Test
	public void testEsacpe3String() throws IOException {
		strategy.escape(writer, "\"Test");
		verify("\"\"Test");
	}

	@Test
	public void testEsacpe4String() throws IOException {
		strategy.escape(writer, "TestA\"TestB");
		verify("TestA\"\"TestB");
	}

	@Test
	public void testEsacpe5String() throws IOException {
		strategy.escape(writer, "TestA\"TestB\"TestC");
		verify("TestA\"\"TestB\"\"TestC");
	}

	private void verify(String text) {
		assertEquals(text, writer.toString());
	}

}
