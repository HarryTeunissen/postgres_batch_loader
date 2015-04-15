package com.ic.persistence.serialize;

import com.ic.persistence.BatchStrategyExcception;

/**
 * 
 * @author HTeunissen
 *
 */
public class SerializationFormatException extends BatchStrategyExcception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3916282767906103793L;

	public SerializationFormatException() {
		super();
	}

	public SerializationFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerializationFormatException(String message) {
		super(message);
	}

	public SerializationFormatException(Throwable cause) {
		super(cause);
	}
	
}
