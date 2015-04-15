package com.ic.persistence;

/**
 * Base Exception from Batch Persistence
 * @author HTeunissen
 *
 */
public class BatchStrategyExcception extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4446483601048841084L;

	public BatchStrategyExcception() {
		super();
	}

	public BatchStrategyExcception(String message, Throwable cause) {
		super(message, cause);
	}

	public BatchStrategyExcception(String message) {
		super(message);
	}

	public BatchStrategyExcception(Throwable cause) {
		super(cause);
	}	
		
}
