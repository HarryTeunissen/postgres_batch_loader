package com.ic.persistence.pipe;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * 
 * @author HTeunissen
 * 
 */
public interface IOPipeService {

	/**
	 * Converts an Iterator<T> into a Reader which can be directly used for IO
	 * 
	 * @param bufferSize
	 *            of Pipe
	 * @param itr
	 * @param serializerFactory
	 * @return Reader (Piped for efficiency)
	 * @throws IOException
	 */
	public <T> Reader createReader(int bufferSize, Iterator<T> itr,
			ObjectSerializerFactory<T> serializerFactory) throws IOException;

	/**
	 * Converts an Iterator<T> into a Reader which can be directly used for IO
	 * 
	 * @param itr
	 * @param serializerFactory
	 * @return
	 * @throws IOException
	 */
	public <T> Reader createReader(Iterator<T> itr,
			ObjectSerializerFactory<T> serializerFactory) throws IOException;

}