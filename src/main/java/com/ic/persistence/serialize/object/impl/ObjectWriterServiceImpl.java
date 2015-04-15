package com.ic.persistence.serialize.object.impl;

import java.io.Writer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ic.persistence.serialize.SerializationFormat;
import com.ic.persistence.serialize.object.ObjectWriter;
import com.ic.persistence.serialize.object.ObjectWriterService;

/**
 * 
 * @author HTeunissen
 *
 */
@Singleton
public class ObjectWriterServiceImpl implements ObjectWriterService {


	@Inject
	public ObjectWriterServiceImpl() {
	}
	
	
	@Override
	public ObjectWriter createObjectWriter(Writer writer, SerializationFormat format) {
		return new DefaultObjectWriter(format, writer) ;
	}

}
