package com.ic.persistence.mapping;

import com.ic.utils.type.Property;
import com.ic.utils.type.PropertyAccessor;

/**
 * PropertyBuilder is used to construct a MappedPropery<T,V>
 * @author HTeunissen
 *
 * @param <T>
 * @param <V>
 */
public interface PropertyBuilder<T, V> {

	/**
	 * 
	 * @param isJoinColumn set to true of this Column is used to Join data with base table
	 * @return Builder 
	 */
	public PropertyBuilder<T, V> setJoinColumn(boolean isJoinColumn);

	/**
	 * 
	 * @param name of Property
	 * @return Builder
	 */
	public PropertyBuilder<T, V> setName(String name);

	/**
	 * 
	 * @param isIdColumn
	 * @return true is the Column represents the Id Column
	 */
	public PropertyBuilder<T, V> setIdColumn(boolean isIdColumn);

	/**
	 * 
	 * @param pa is the PropertyAccessor 
	 * that will return value V from Object T for Mapped Property
	 * @return PropertyBuilder
	 */
	public PropertyBuilder<T, V> setPropertyAccessor(
			PropertyAccessor<T, V> pa);

	/**
	 * 
	 * @param property can be used to extract attributes for MappedProperty
	 * @return PropertyBuilder
	 */
	public PropertyBuilder<T, V> setProperty(Property property);

	/**
	 * 
	 * @param columnName of Underlying DataModel
	 * @return
	 */
	public PropertyBuilder<T, V> setColumnName(String columnName);

	/**
	 * 
	 * @return
	 * @throws ObjectMappingException
	 */
	public MappedProperty<T> build() throws ObjectMappingException ;

}