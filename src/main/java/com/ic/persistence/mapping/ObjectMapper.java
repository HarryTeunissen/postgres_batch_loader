package com.ic.persistence.mapping;

import java.util.Collection;

/**
 * 
 * @author HTeunissen
 * 
 * @param <T>
 */
public interface ObjectMapper<T> {

	/**
	 * Maintains Basic Mapping information Between Model and Persistence
	 * 
	 * @return TableReference
	 */
	public TableReference getTableReference();

	/**
	 * 
	 * @return Join Column
	 */
	public Column getJoinColumn();

	/**
	 * 
	 * @return all mapped database columns
	 */
	public Collection<Column> getAllColumns();

	/**
	 * 
	 * @return all Columns that are not Id columns.
	 */
	public Collection<Column> getIncludedColumns();

	/**
	 * 
	 * @return
	 */
	public Collection<MappedProperty<T>> getMappedProperties();

}