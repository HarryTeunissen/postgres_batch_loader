package com.ic.persistence.serialize.property;

import com.ic.utils.type.PropertyAccessor;

/**
 * 
 * @author HTeunissen
 *
 */
public interface PropertyWriterService {

	/**
	 *  Generates a PropertyWriter<T> for a given PropertyAccessor 
	 *
	 * @param accessor
	 * @return PropertyWriter that writes directly into ObjectSerializer
	 */
	public <T, V> PropertyWriter<T> createPropertyWriter(
			PropertyAccessor<T, V> accessor);

}
