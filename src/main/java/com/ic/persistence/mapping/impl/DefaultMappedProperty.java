package com.ic.persistence.mapping.impl;

import com.ic.persistence.mapping.Column;
import com.ic.persistence.mapping.MappedProperty;
import com.ic.persistence.serialize.property.PropertyWriter;
import com.ic.persistence.serialize.property.PropertyWriterService;
import com.ic.utils.type.PropertyAccessor;

/**
 * 
 * @author HTeunissen
 *
 * @param <V>
 * @param <T>
 */
public class DefaultMappedProperty<V, T> implements MappedProperty<T>{

	private String name;
	private Class<V> clz;

	private boolean idColumn;
	private boolean joinColumn;

	private Column column;

	private PropertyAccessor<T, V> accessor;

	public DefaultMappedProperty(String name, Class<V> clz, boolean idColumn,
			boolean joinColumn, Column column, PropertyAccessor<T, V> accessor) {
		super();
		this.name = name;
		this.clz = clz;
		this.idColumn = idColumn;
		this.joinColumn = joinColumn;
		this.column = column;
		this.accessor = accessor;
	}


	public Column getColumn() {
		return column;
	}

	public PropertyAccessor<T, V> getAccessor() {
		return accessor;
	}


	@Override
	public boolean isJoinProperty() {
		return joinColumn;

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<?> getPropertyType() {
		return clz;
	}

	@Override
	public boolean isIdColumn() {
		return idColumn;
	}
	

	@Override
	public PropertyWriter<T> createPropertyWriter(
			PropertyWriterService propertyWriterFactory) {
		return propertyWriterFactory.createPropertyWriter(accessor) ;
	}
	
}
