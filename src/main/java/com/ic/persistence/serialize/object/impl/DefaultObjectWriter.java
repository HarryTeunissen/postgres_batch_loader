package com.ic.persistence.serialize.object.impl;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ic.persistence.serialize.SerializationFormat;
import com.ic.persistence.serialize.object.ObjectWriter;
import com.ic.persistence.serialize.object.escape.QuoteEscapeStrategy;
import com.ic.persistence.serialize.object.escape.TextEscapeStrategy;
import com.ic.persistence.serialize.object.escape.UnesecapedStrategy;

/**
 * 
 * @author HTeunissen
 *
 */
public class DefaultObjectWriter implements ObjectWriter {

	private final transient SerializationFormat format;
	private final transient Writer writer;
	private transient TextEscapeStrategy escapeStrategy;
	private transient DateFormat dateFormat;

	private int count = 0;

	public DefaultObjectWriter(SerializationFormat format, Writer writer) {
		super();
		this.format = format;
		this.writer = writer;

		configure(format);
	}

	private void configure(SerializationFormat format) {

		dateFormat = new SimpleDateFormat(format.getDateFormat());

		escapeStrategy = format.isEscapeQuotes() ? new QuoteEscapeStrategy(
				format.getQuoteChar()) : UnesecapedStrategy.STRATEGY;
	}

	@Override
	public void flush() throws IOException {
		writer.flush();
	}

	@Override
	public void close() throws IOException {
		writer.close();
	}

	@Override
	public void startObject() {
		count = 0;
	}

	private void _write(String s) throws IOException {
		writer.write(s);
	}

	private void nextValue() throws IOException {
		if (count++ > 0) {
			_write(format.getValueSeparator());
		}
	}

	private void _write(char c) throws IOException {
		writer.write(c);
	}

	@Override
	public void endObject() throws IOException {
		if (count > 0) {
			_write(format.getObjectSeperator());
		}
	}

	@Override
	public void write(Boolean value) throws IOException {
		nextValue();
		if (value == null) {
			_writeNull();
		} else {
			_write(value ? format.getBooleanTrue() : format.getBooleanFalse());
		}
	}

	@Override
	public void write(String s) throws IOException {
		nextValue();
		if (s == null) {
			_writeNull();
		} else {
			_write(format.getQuoteChar());
			escapeStrategy.escape(writer, s);
			_write(format.getQuoteChar());
		}
	}

	@Override
	public void write(Date date) throws IOException {
		nextValue();
		if (date == null) {
			_writeNull();
		} else {
			_write(dateFormat.format(date));
		}
	}

	@Override
	public void write(Number number) throws IOException {
		nextValue();
		if (number == null) {
			_writeNull();
		} else {
			_write(number.toString());
		}
	}

	private void _writeNull() throws IOException {
		_write(format.getNullValue());
	}
	
	public void writeNull() throws IOException {
		nextValue();
		_writeNull();
	}

}
