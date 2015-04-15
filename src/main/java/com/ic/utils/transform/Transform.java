package com.ic.utils.transform;

public interface Transform<S, D> {
	
	public D converter(S src) ;

}
