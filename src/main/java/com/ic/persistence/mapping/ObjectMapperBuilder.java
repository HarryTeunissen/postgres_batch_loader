package com.ic.persistence.mapping;

/**
 * 
 * 
 * @author HTeunissen
 *
 * @param <T>
 */
public interface ObjectMapperBuilder<T> {


	/**
	 * Bind schema, tableName.
	 * 
	 * @param schema
	 * @param tableName
	 * @return ObjectMappedBuilder<T>
	 */
	public ObjectMapperBuilder<T> bindTable(String schema, String tableName);

	/**
	 * 
	 * @param mapped
	 * @return ObjectMappedBuilder<T>
	 */
	public ObjectMapperBuilder<T> addProperty(MappedProperty<T> mapped);

	/**
	 * 
	 * @return ObjectMapper<T>
	 */
	public ObjectMapper<T> build() throws ObjectMappingException;

}