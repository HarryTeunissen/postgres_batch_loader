package com.ic.persistence.mapping.impl;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ic.persistence.mapping.MappedProperty;
import com.ic.persistence.mapping.ObjectMapperBuilder;
import com.ic.persistence.mapping.ObjectMapperService;
import com.ic.persistence.mapping.ObjectMappingException;
import com.ic.persistence.mapping.PropertyBuilder;
import com.ic.utils.type.Property;
import com.ic.utils.type.TypeMapper;

/**
 * 
 * @author HTeunissen
 *
 */
@Singleton
public class ObjectMapperServiceImpl implements ObjectMapperService {

	@Inject
	public ObjectMapperServiceImpl() {
	}

	@Override
	public <T> ObjectMapperBuilder<T> objectMapper(Class<T> clz) {
		return new DefaultObjectMapperBuilder<>(clz);
	}

	@Override
	public <T, V> PropertyBuilder<T, V> propertyBuilder(Class<T> objectType,
			Class<V> propertyType) {
		return new DefaultPropertyBuilder<T, V>(objectType, propertyType);
	}

	/**
	 * populate ObjectMappingBuilder using Hibernate annotations. 
	 * This is designed for re-use and can be swapped out for any appropriate strategy
	 */
	@Override
	public <T> ObjectMapperBuilder<T> bindObject(Class<T> clz)
			throws ObjectMappingException {

		ObjectMapperBuilder<T> b = objectMapper(clz);
		Table table = clz.getAnnotation(Table.class);

		if (table == null) {
			throw new ObjectMappingException(clz.getName()
					+ " has no hibername table");
		}

		b.bindTable(null, table.name());

		for (Property property : TypeMapper.MAPPER.getPropertyMap(clz).values()) {
			javax.persistence.Column col = property
					.getAnnotation(javax.persistence.Column.class);
			if (col != null) {
				b.addProperty(bindProperty(clz, property, col));
			}
		}

		return b;
	}

	/**
	 * Maps a hibernate annotated Property into a MappedProperty<T> 
	 * @param objectType
	 * @param property
	 * @param col
	 * @return
	 * @throws ObjectMappingException
	 */
	private <T> MappedProperty<T> bindProperty(Class<T> objectType,
			Property property, javax.persistence.Column col)
			throws ObjectMappingException {

		Class<?> clz = property.getGetterMethod().getReturnType();
		return propertyBuilder(objectType, clz).setProperty(property)
				.setColumnName(col.name())
				.setJoinColumn(property.getAnnotation(NaturalId.class) != null)
				.setIdColumn(property.getAnnotation(Id.class) != null).build();

	}

}
