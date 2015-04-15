package com.ic.persistence.mapping;

/**
 * ObjectMapperService builds ObjectMapping 
 * @author HTeunissen
 *
 */
public interface ObjectMapperService {

	/**
	 * 
	 * @param objectType
	 * @param proertyType
	 * @return a PropertyBuilder<T,V>
	 */
	public <T, V> PropertyBuilder<T, V> propertyBuilder(Class<T> objectType, Class<V> proertyType) ;
	
	/**
	 * Creates a new ObjectMapperBuilder<T> for a Class<T>
	 * 
	 * @param clz
	 * @return ObjectMapperBuilder<T>
	 */
	public <T> ObjectMapperBuilder<T> objectMapper(Class<T> clz) ;
	
	/**
	 * 
	 * @param clz
	 * @return a populated ObjectMapper based. (Currently Hibernate mappings are resused)
	 * @throws ObjectMappingException
	 */
	public <T> ObjectMapperBuilder<T> bindObject(Class<T> clz) throws ObjectMappingException;

}
