package com.ic.persistence.strategy;

import java.util.Iterator;

/**
 *  
 * @author HTeunissen
 *
 * @param <T>
 */
public interface BatchCommandContext<T> {
	
	/**
	 * 
	 * @return iterator of values 
	 * (which may be very large and stream through memory)
	 */
	public Iterator<T> iterator() ;
	
}
