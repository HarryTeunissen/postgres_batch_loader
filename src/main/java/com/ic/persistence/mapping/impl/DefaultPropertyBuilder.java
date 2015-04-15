package com.ic.persistence.mapping.impl;

import java.lang.reflect.Method;

import com.ic.persistence.mapping.Column;
import com.ic.persistence.mapping.MappedProperty;
import com.ic.persistence.mapping.ObjectMappingException;
import com.ic.persistence.mapping.PropertyBuilder;
import com.ic.utils.type.Property;
import com.ic.utils.type.PropertyAccessor;

/**
 * 
 * @author HTeunissen
 *
 * @param <T>
 * @param <V>
 */
class DefaultPropertyBuilder<T, V> implements PropertyBuilder<T, V> {

	private Class<T> objectType ;
	private Class<V> clz;

	private String propertyName;
	private boolean joinColumn;
	private boolean idColumn;
	private PropertyAccessor<T, V> propertyAccessor;
	private Column column;

	DefaultPropertyBuilder(Class<T> objectType, Class<V> clz) {
		super();
		
		this.objectType = objectType ;
		this.clz = clz;
		
		if( objectType ==null || clz == null) {
			throw new NullPointerException() ;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.mapping.PropertyBuilder#setJoinColumn(boolean)
	 */
	public PropertyBuilder<T, V> setJoinColumn(boolean isJoinColumn) {
		joinColumn = isJoinColumn;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.mapping.PropertyBuilder#setName(java.lang.String)
	 */
	public PropertyBuilder<T, V> setName(String name) {
		this.propertyName = name;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.mapping.PropertyBuilder#setIdColumn(boolean)
	 */
	public PropertyBuilder<T, V> setIdColumn(boolean isIdColumn) {
		idColumn = isIdColumn;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ic.persistence.mapping.PropertyBuilder#setPropertyAccessor(com.ic
	 * .utils.type.PropertyAccessor)
	 */
	public PropertyBuilder<T, V> setPropertyAccessor(PropertyAccessor<T, V> pa) {
		this.propertyAccessor = pa;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ic.persistence.mapping.PropertyBuilder#setProperty(com.ic.utils.type
	 * .Property)
	 */
	@SuppressWarnings({ "unchecked" })
	public PropertyBuilder<T, V> setProperty(final Property property) {
		setName(property.getPropertyName());
		final Method getter = property.getGetterMethod();

		setPropertyAccessor(new PropertyAccessor<T, V>() {
			
			@Override
			public Class<V> getAccessorType() {
				return (Class<V>) property.getPropertyClass() ;
			}

			@Override
			public V getValue(T object) throws Exception {
				return (V) getter.invoke(object);
			}
		});

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ic.persistence.mapping.PropertyBuilder#setColumnName(java.lang.String
	 * )
	 */
	public PropertyBuilder<T, V> setColumnName(String columnName) {
		this.column = new Column(columnName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.mapping.PropertyBuilder#build()
	 */
	public MappedProperty<T> build() throws ObjectMappingException {
		
		if( propertyName == null ) {
			throw new ObjectMappingException("No Property Name defined for " + toString()) ;
		}
		
		if( column == null ) {
			throw new ObjectMappingException("No column defined for "  + toString()) ;
		}
		
		if( propertyAccessor == null ) {
			throw new ObjectMappingException("No PropertyAcessor defined for "  + toString()) ;
		}
		
		return new DefaultMappedProperty<>(propertyName, clz, idColumn,
				joinColumn, column, propertyAccessor);
	}
	
	@Override
	public String toString() {
		return objectType.getName() + "." + propertyName + "(" +  clz.getName() + ")" ;
	}
}
