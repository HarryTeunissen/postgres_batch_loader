package com.ic.persistence.mapping.impl;

import java.util.ArrayList;
import java.util.List;

import com.ic.persistence.mapping.MappedProperty;
import com.ic.persistence.mapping.ObjectMapper;
import com.ic.persistence.mapping.ObjectMapperBuilder;
import com.ic.persistence.mapping.ObjectMappingException;
import com.ic.persistence.mapping.TableReference;
import com.ic.utils.transform.Filter;
import com.ic.utils.type.MappingUtils;

/**
 * 
 * @author HTeunissen
 *
 * @param <T>
 */
class DefaultObjectMapperBuilder<T> implements ObjectMapperBuilder<T> {

	
	//
	// Binding Details given
	//
	private final transient List<MappedProperty<T>> mappedProperties = new ArrayList<>();
	private transient TableReference tableReference;

	DefaultObjectMapperBuilder(Class<T> clz) {
		super();
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ic.persistence.mapping.ObjectMapperBuilder#bindTable(java.lang.String
	 * , java.lang.String)
	 */
	public ObjectMapperBuilder<T> bindTable(String schema, String tableName) {
		this.tableReference = new TableReference(schema, tableName);
		return this;
	}

	@Override
	public ObjectMapperBuilder<T> addProperty(MappedProperty<T> property) {
		mappedProperties.add(property);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.mapping.ObjectMapperBuilder#build()
	 */
	public ObjectMapper<T> build() throws ObjectMappingException {
		
		if(mappedProperties.size() == 0 ) {
			throw new ObjectMappingException("No Propertied defined") ;
		}
		
		MappedProperty<T> joinCol= MappingUtils.findFirst(mappedProperties,
				new Filter<MappedProperty<T>>() {
					@Override
					public boolean isMember(MappedProperty<T> value) {
						return value.isJoinProperty();
					}
				});
		
		if( joinCol == null ) {
			throw new ObjectMappingException("No Join Column defined") ;
		}
		
		return new DefaultObjectMapper<T>(tableReference, mappedProperties, joinCol);
	}

}
