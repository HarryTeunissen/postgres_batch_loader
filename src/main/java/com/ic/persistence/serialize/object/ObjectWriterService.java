package com.ic.persistence.serialize.object;

import java.io.Writer;

import com.ic.persistence.serialize.SerializationFormat;

/**
 * 
 * @author HTeunissen
 *
 */
public interface ObjectWriterService {

	/**
	 * 
	 * @param writer
	 * @param format
	 * @return ObjectWriter defined by SerializationFormat
	 */
	public ObjectWriter createObjectWriter(Writer writer,  SerializationFormat format) ;
	
}
