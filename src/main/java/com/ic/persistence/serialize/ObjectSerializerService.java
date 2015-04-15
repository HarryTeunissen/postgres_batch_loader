package com.ic.persistence.serialize;

import java.io.Writer;
import java.util.Collection;

import com.ic.persistence.mapping.MappedProperty;
import com.ic.persistence.serialize.object.ObjectWriter;

/**
 * 
 * @author HTeunissen
 *
 */
public interface ObjectSerializerService {

	/**
	 * 
	 * @param mapping collection of MappedProperties
	 * @param writer Character writer
	 * @return an ObjectSerializer<T> that generates CSV 
	 */
	public <T> ObjectSerializer<T> createObjectSerializer(
			Collection<MappedProperty<T>> mapping, ObjectWriter writer);

	/**
	 * 
	 * @param mapping
	 * @param writer
	 * @param format
	 * @return
	 */
	public <T> ObjectSerializer<T> createObjectSerializer(
			Collection<MappedProperty<T>> mapping, Writer writer, SerializationFormat format);

	
}