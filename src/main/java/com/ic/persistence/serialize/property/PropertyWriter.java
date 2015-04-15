package com.ic.persistence.serialize.property;

import com.ic.persistence.serialize.object.ObjectWriter;


/**
 * 
 * @author HTeunissen
 *
 * @param <T>
 */
public interface PropertyWriter<T> {
	
	/**
	 * Write a property of T into ObjectWriter
	 * @param value
	 * @param writer
	 * @throws Exception
	 */
	public void write(T value, ObjectWriter writer) throws Exception ;

}
