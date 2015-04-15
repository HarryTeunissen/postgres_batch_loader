package com.ic.persistence.serialize.object.escape;

import java.io.IOException;
import java.io.Writer;

/**
 * 
 * Serializers (CSV) need to escape String literals containing Quotes.
 * This API is designed for specialized escape strategies that can write directly. 
 * @author HTeunissen
 *
 */
public interface TextEscapeStrategy {

	public void escape(Writer writer, String s) throws IOException ;
	
}
