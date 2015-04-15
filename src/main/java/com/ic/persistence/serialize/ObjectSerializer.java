package com.ic.persistence.serialize;

import java.io.IOException;

/**
 * 
 * @author HTeunissen
 * 
 * ObjectSerializer serializes a stream of values 
 * 
 * @param <T>
 */
public interface ObjectSerializer<T> {

	/**
	 * Write Value
	 * @param value
	 * @throws Exception
	 */
	public void write(T value) throws Exception;

	/**
	 * Flush stream operation where possible
	 * @throws IOException
	 */
	public void flush() throws IOException;

	/**
	 * 
	 * close Serializer 
	 */
	public void close() throws IOException;

}