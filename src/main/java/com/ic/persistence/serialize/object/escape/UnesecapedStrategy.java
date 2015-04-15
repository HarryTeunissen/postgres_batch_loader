package com.ic.persistence.serialize.object.escape;

import java.io.IOException;
import java.io.Writer;

/**
 * 
 * @author HTeunissen
 *
 */
public class UnesecapedStrategy implements TextEscapeStrategy {

	public static final TextEscapeStrategy STRATEGY = new UnesecapedStrategy() ;
	
	private UnesecapedStrategy() {
	}
	
	@Override
	public void escape(Writer writer, String s) throws IOException {
		writer.write(s) ;
	}	
	
}
