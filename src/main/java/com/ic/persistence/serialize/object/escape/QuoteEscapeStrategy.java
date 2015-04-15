package com.ic.persistence.serialize.object.escape;

import java.io.IOException;
import java.io.Writer;

/**
 * 
 * @author HTeunissen
 *
 */
public class QuoteEscapeStrategy implements TextEscapeStrategy {

	
	
	private final char escapedChar;

	public QuoteEscapeStrategy(char quote) {
		super();
		this.escapedChar = quote;
	}

	/**
	 * Simple escape impl
	 */
	@Override
	public void escape(Writer writer, String s) throws IOException {
		
		if( s == null ) { //Safeguard boundary case
			return ;
		}
		
		int startIndex = 0;
		int index = s.indexOf(escapedChar);

		if (index >= 0) {
			do {
				writer.write(s.substring(startIndex, index + 1));
				writer.write(escapedChar);
				startIndex = index + 1;
				index = s.indexOf(escapedChar, startIndex);
			} while (index >= 0);
		}

		writer.write(s.substring(startIndex));
		
	}

}
