package com.ic.persistence.serialize.impl;

import com.google.inject.Singleton;
import com.ic.persistence.serialize.SequenceStrategyFactory;
import com.ic.persistence.serialize.object.ObjectWriter;
import com.ic.persistence.serialize.property.PropertyWriter;

/**
 * Super Simple Sequence Genration Strategy
 * @author HTeunissen
 *
 */
@Singleton
public class SequenceStrategyFactoryImpl implements SequenceStrategyFactory {


	public SequenceStrategyFactoryImpl() {
	}

	/**
	 * 
	 * @author HTeunissen Long Generator
	 * @param <T>
	 */
	private static class LongGenerator<T> implements PropertyWriter<T> {
		private long seqNumber = 0;

		@Override
		public void write(T value, ObjectWriter writer) throws Exception {
			writer.write(seqNumber++);
		}
	}

	/**
	 * 
	 * @author HTeunissen IntGenerator
	 * @param <T>
	 */
	private static class IntGenerator<T> implements PropertyWriter<T> {
		private int seqNumber = 0;

		@Override
		public void write(T value, ObjectWriter writer) throws Exception {
			writer.write(seqNumber++);
		}
	}

	/* (non-Javadoc)
	 * @see com.ic.persistence.serialize.SequenceStrategyFactory#create(java.lang.Class)
	 */
	public <T> PropertyWriter<T> create(Class<?> idType) {

		if (idType.equals(Long.class) || idType.equals(long.class)) {
			return new LongGenerator<T>();
		}

		if (idType.equals(Integer.class) || idType.equals(int.class)) {
			return new IntGenerator<T>();
		}

		return new IntGenerator<T>();

	}

}
