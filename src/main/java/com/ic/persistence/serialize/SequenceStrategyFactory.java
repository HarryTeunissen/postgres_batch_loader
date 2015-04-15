package com.ic.persistence.serialize;

import com.ic.persistence.serialize.property.PropertyWriter;

/**
 * Abstracts a resetable SequenceGeneator.
 * (typically used to generate ID's when not supported by underlying database)
 * @author HTeunissen
 *
 */
public interface SequenceStrategyFactory {

	/**
	 * 
	 * @param idType
	 * @return PropertyWriter<T> for creates a sequence generator
	 */
	public <T> PropertyWriter<T> create(Class<?> idType);

}