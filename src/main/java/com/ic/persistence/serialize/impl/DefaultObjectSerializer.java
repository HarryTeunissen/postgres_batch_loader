package com.ic.persistence.serialize.impl;

import java.io.IOException;
import java.util.Collection;

import com.ic.persistence.serialize.ObjectSerializer;
import com.ic.persistence.serialize.object.ObjectWriter;
import com.ic.persistence.serialize.property.PropertyWriter;

/**
 * 
 * @author HTeunissen
 *
 * @param <T>
 */
class DefaultObjectSerializer<T> implements ObjectSerializer<T> {

	private final transient Collection<PropertyWriter<T>> mapping;
	private final transient ObjectWriter writer;

	DefaultObjectSerializer(final ObjectWriter writer,
			final Collection<PropertyWriter<T>> mapping) {
		super();
		this.mapping = mapping;
		this.writer = writer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.io.ObjectSerializer#write(T)
	 */
	public void write(T value) throws Exception {
		writer.startObject();
		for (PropertyWriter<T> p : mapping) {
			p.write(value, writer);
		}
		writer.endObject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.io.ObjectSerializer#flush()
	 */
	public void flush() throws IOException {
		writer.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.io.ObjectSerializer#close()
	 */
	public void close() throws IOException {
		writer.close();
	}

}
