package com.ic.persistence.mapping.impl;

import java.util.Collection;
import java.util.Collections;

import com.ic.persistence.mapping.Column;
import com.ic.persistence.mapping.MappedProperty;
import com.ic.persistence.mapping.ObjectMapper;
import com.ic.persistence.mapping.TableReference;
import com.ic.utils.transform.Filter;
import com.ic.utils.transform.Transform;
import com.ic.utils.type.MappingUtils;

/**
 * 
 * @author HTeunissen
 *
 * @param <T>
 */
public class DefaultObjectMapper<T> implements ObjectMapper<T> {

	private TableReference tableReference;
	private Collection<MappedProperty<T>> mapping;

	private Collection<MappedProperty<T>> includedmapping;

	private final transient  MappedProperty<T> joinProperty;
	private final transient Collection<Column> columns;
	private final transient Collection<Column> allColumns;

	public DefaultObjectMapper(TableReference tableReference,
			Collection<MappedProperty<T>> mapping, MappedProperty<T> joinProperty) {
		super();

		this.tableReference = tableReference;
		this.mapping = Collections.unmodifiableCollection(mapping);
		this.joinProperty = joinProperty ;
		
		includedmapping = Collections.unmodifiableCollection(MappingUtils
				.filter(mapping, new Filter<MappedProperty<T>>() {
					@Override
					public boolean isMember(MappedProperty<T> value) {
						return !value.isIdColumn();
					}
				}));

		allColumns = Collections.unmodifiableCollection(MappingUtils.transform(
				this.mapping, new Transform<MappedProperty<T>, Column>() {
					@Override
					public Column converter(MappedProperty<T> src) {
						return src.getColumn();
					}
				}));

		columns = Collections.unmodifiableCollection(MappingUtils.transform(
				this.includedmapping,
				new Transform<MappedProperty<T>, Column>() {
					@Override
					public Column converter(MappedProperty<T> src) {
						return src.getColumn();
					}
				}));

	}

	@Override
	public Collection<MappedProperty<T>> getMappedProperties() {
		return mapping;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.mapping.OM#getTableReference()
	 */
	public TableReference getTableReference() {
		return tableReference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.mapping.OM#getJoinColumn()
	 */
	public Column getJoinColumn() {
		return joinProperty.getColumn();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.mapping.OM#getAllColumns()
	 */
	public Collection<Column> getAllColumns() {
		return allColumns;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ic.persistence.mapping.OM#getIncludedColumns()
	 */
	public Collection<Column> getIncludedColumns() {
		return columns;
	}

}
