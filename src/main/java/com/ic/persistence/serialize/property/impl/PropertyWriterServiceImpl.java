package com.ic.persistence.serialize.property.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Singleton;
import com.ic.persistence.serialize.object.ObjectWriter;
import com.ic.persistence.serialize.property.PropertyWriter;
import com.ic.persistence.serialize.property.PropertyWriterService;
import com.ic.utils.type.PropertyAccessor;

/**
 * 
 * @author HTeunissen
 *
 */
@Singleton
public class PropertyWriterServiceImpl implements PropertyWriterService {

	private Map<Class<?>, WriterFactory<?>> map = new HashMap<>();

	public PropertyWriterServiceImpl() {
		init();
	}

	@Override
	public <T, V> PropertyWriter<T> createPropertyWriter(
			PropertyAccessor<T, V> accessor) {

		WriterFactory<V> f = getFactory(accessor.getAccessorType());
		return f == null ? new ObjectPropertyWriter<>(accessor) : f
				.createWriter(accessor, accessor.getAccessorType());
	}

	@SuppressWarnings("unchecked")
	private <V> WriterFactory<V> getFactory(Class<V> clz) {
		return (WriterFactory<V>) map.get(clz);
	}

	private interface WriterFactory<V> {
		public <T> PropertyWriter<T> createWriter(
				PropertyAccessor<T, V> accessor, Class<V> valueType);
	}

	private <T, C extends T> void register(Class<C> clz,
			WriterFactory<T> factory) {
		map.put(clz, factory);
	}

	/**
	 * Initialize basic Writer Strategies
	 */
	private void init() {

		register(Object.class, new WriterFactory<Object>() {
			@Override
			public <T> PropertyWriter<T> createWriter(
					PropertyAccessor<T, Object> accessor,
					Class<Object> valueType) {
				return new ObjectPropertyWriter<>(accessor);
			}
		});

		register(String.class, new WriterFactory<String>() {
			@Override
			public <T> PropertyWriter<T> createWriter(
					PropertyAccessor<T, String> accessor,
					Class<String> valueType) {
				return new StringPropertyWriter<>(accessor);
			}
		});

		final WriterFactory<Number> numberFactory = new WriterFactory<Number>() {
			@Override
			public <T> PropertyWriter<T> createWriter(
					PropertyAccessor<T, Number> accessor,
					Class<Number> valueType) {
				return new NumberPropertyWriter<>(accessor);
			}
		};

		register(Long.class, numberFactory);
		register(long.class, numberFactory);
		register(Integer.class, numberFactory);
		register(int.class, numberFactory);
		register(Short.class, numberFactory);
		register(short.class, numberFactory);
		register(Double.class, numberFactory);
		register(double.class, numberFactory);
		register(Float.class, numberFactory);
		register(float.class, numberFactory);

		final WriterFactory<Date> dateFactory = new WriterFactory<Date>() {
			@Override
			public <T> PropertyWriter<T> createWriter(
					PropertyAccessor<T, Date> accessor, Class<Date> valueType) {
				return new DatePropertyWriter<>(accessor);
			}
		};

		register(Date.class, dateFactory);
		register(java.sql.Date.class, dateFactory);

		final WriterFactory<Boolean> booleanFactory = new WriterFactory<Boolean>() {
			@Override
			public <T> PropertyWriter<T> createWriter(
					PropertyAccessor<T, Boolean> accessor,
					Class<Boolean> valueType) {
				return new BooleanPropertyWriter<>(accessor);
			}
		};

		register(Boolean.class, booleanFactory);
		register(boolean.class, booleanFactory);
	}

	/**
	 * 
	 * @author HTeunissen
	 *
	 * @param <T>
	 * @param <V>
	 */
	private static class NumberPropertyWriter<T, V extends Number> implements
			PropertyWriter<T> {

		private PropertyAccessor<T, V> accessor;

		public NumberPropertyWriter(PropertyAccessor<T, V> accessor) {
			super();
			this.accessor = accessor;
		}

		@Override
		public void write(T value, ObjectWriter writer) throws Exception {
			writer.write(accessor.getValue(value));
		}

	}

	/**
	 * 
	 * @author HTeunissen
	 *
	 * @param <T>
	 */
	private static class StringPropertyWriter<T> implements PropertyWriter<T> {
		private PropertyAccessor<T, String> accessor;

		public StringPropertyWriter(PropertyAccessor<T, String> accessor) {
			super();
			this.accessor = accessor;
		}

		@Override
		public void write(T value, ObjectWriter writer) throws Exception {
			writer.write(accessor.getValue(value));
		}
	}

	/**
	 * 
	 * @author HTeunissen
	 *
	 * @param <T>
	 */
	private static class BooleanPropertyWriter<T> implements PropertyWriter<T> {
		private PropertyAccessor<T, Boolean> accessor;

		public BooleanPropertyWriter(PropertyAccessor<T, Boolean> accessor) {
			super();
			this.accessor = accessor;
		}

		@Override
		public void write(T value, ObjectWriter writer) throws Exception {
			writer.write(accessor.getValue(value));
		}
	}

	/**
	 * 
	 * @author HTeunissen
	 *
	 * @param <T>
	 */
	private static class DatePropertyWriter<T> implements PropertyWriter<T> {
		private PropertyAccessor<T, Date> accessor;

		public DatePropertyWriter(PropertyAccessor<T, Date> accessor) {
			super();
			this.accessor = accessor;
		}

		@Override
		public void write(T value, ObjectWriter writer) throws Exception {
			writer.write(accessor.getValue(value));
		}
	}

	/**
	 * ObjectWriter
	 * @author HTeunissen
	 *
	 * @param <T>
	 */
	private static class ObjectPropertyWriter<T> implements PropertyWriter<T> {
		private PropertyAccessor<T, ? extends Object> accessor;

		public ObjectPropertyWriter(
				PropertyAccessor<T, ? extends Object> accessor) {
			super();
			this.accessor = accessor;
		}

		@Override
		public void write(T value, ObjectWriter writer) throws Exception {

			Object val = accessor.getValue(value);
			writer.write(val == null ? null : val.toString());
		}
	}

}
