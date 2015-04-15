package com.ic.persistence.pipe;

import java.io.Writer;

import com.ic.persistence.serialize.ObjectSerializer;

/**
 * Used by IOPipeService as callback to write Iterator<T> directly into writer stream
 * 
 * @author HTeunissen
 * 
 * @param <T>
 */
public interface ObjectSerializerFactory<T> {

	/**
	 * 
	 * @param writer
	 * @return  ObjectSerializer<T> bound to Writer
	 */
	public ObjectSerializer<T> create(Writer writer);

}
