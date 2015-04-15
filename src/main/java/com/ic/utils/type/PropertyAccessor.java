package com.ic.utils.type;

public interface PropertyAccessor<T, V> {
	
	public Class<V> getAccessorType() ;
	public V getValue(T object) throws Exception ;
	

}
