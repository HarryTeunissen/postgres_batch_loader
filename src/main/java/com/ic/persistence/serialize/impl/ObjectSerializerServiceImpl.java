package com.ic.persistence.serialize.impl;

import java.io.Writer;
import java.util.Collection;

import com.google.inject.Inject;
import com.ic.persistence.mapping.MappedProperty;
import com.ic.persistence.serialize.ObjectSerializer;
import com.ic.persistence.serialize.ObjectSerializerService;
import com.ic.persistence.serialize.SequenceStrategyFactory;
import com.ic.persistence.serialize.SerializationFormat;
import com.ic.persistence.serialize.object.ObjectWriter;
import com.ic.persistence.serialize.object.ObjectWriterService;
import com.ic.persistence.serialize.property.PropertyWriter;
import com.ic.persistence.serialize.property.PropertyWriterService;
import com.ic.utils.transform.Transform;
import com.ic.utils.type.MappingUtils;

/**
 * 
 * @author HTeunissen
 * 
 */
public class ObjectSerializerServiceImpl implements ObjectSerializerService {

	private PropertyWriterService propertyWriterFactory;
	private SequenceStrategyFactory sequenceStrategyFactory;
	private ObjectWriterService objectWriterService;

	@Inject
	public ObjectSerializerServiceImpl(
			PropertyWriterService propertyWriterFactory,
			SequenceStrategyFactory sequenceStrategyFactory,
			ObjectWriterService objectWriterService) {
		super();
		this.propertyWriterFactory = propertyWriterFactory;
		this.sequenceStrategyFactory = sequenceStrategyFactory;
		this.objectWriterService = objectWriterService;
	}

	public <T> ObjectSerializer<T> _createWriter(
			Collection<PropertyWriter<T>> mapping, ObjectWriter writer) {
		return new DefaultObjectSerializer<T>(writer, mapping);
	}

	@Override
	public <T> ObjectSerializer<T> createObjectSerializer(
			Collection<MappedProperty<T>> mapping, ObjectWriter writer) {
		return _createWriter(MappingUtils.transform(mapping,
				new Transform<MappedProperty<T>, PropertyWriter<T>>() {
					@Override
					public PropertyWriter<T> converter(MappedProperty<T> src) {

						if (src.isIdColumn()) {
							return sequenceStrategyFactory.create(src
									.getPropertyType());
						}

						return src.createPropertyWriter(propertyWriterFactory);
					}

				}), writer);

	}

	@Override
	public <T> ObjectSerializer<T> createObjectSerializer(
			Collection<MappedProperty<T>> mapping, Writer writer,
			SerializationFormat format) {
		return createObjectSerializer(mapping,
				objectWriterService.createObjectWriter(writer, format));
	}

}
