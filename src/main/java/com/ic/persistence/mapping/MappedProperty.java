package com.ic.persistence.mapping;

import com.ic.persistence.serialize.property.PropertyWriter;
import com.ic.persistence.serialize.property.PropertyWriterService;



/**
 * 
 * MappedProperty<T> represents that Mapping between a Property and a database Column
 * 
 * @author HTeunissen
 *
 * @param <T>
 */
public interface MappedProperty<T> {
	
	/**
	 * 
	 * @return Name of Property
	 */
	public String getName() ;
	
	/**
	 * 
	 * @return Property Type
	 */
	public Class<?> getPropertyType() ;
	
	
	/**
	 * 
	 * @return true if column represents PK
	 */
	public boolean isIdColumn() ;
	
	/**
	 * 
	 * @return true is join Property (Used for updates and inserts (ant-join pattern))
	 */
	public boolean isJoinProperty() ;
	
	/**
	 * 
	 * @return 
	 */
	public Column getColumn() ;
	
	/**
	 * 
	 * @return a PropertyWriter<T> for serializing this Property
	 */
	public PropertyWriter<T> createPropertyWriter(PropertyWriterService propertyWriterFactory) ;
}
