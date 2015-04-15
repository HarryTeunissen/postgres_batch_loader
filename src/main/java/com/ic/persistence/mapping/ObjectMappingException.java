package com.ic.persistence.mapping;

import com.ic.persistence.BatchStrategyExcception;


public class ObjectMappingException extends BatchStrategyExcception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8486576990259865141L;

	public ObjectMappingException() {
		super();
	}

	public ObjectMappingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectMappingException(String message) {
		super(message);
	}

	public ObjectMappingException(Throwable cause) {
		super(cause);
	}

}
