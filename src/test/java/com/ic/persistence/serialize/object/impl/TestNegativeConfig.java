package com.ic.persistence.serialize.object.impl;

import org.junit.Before;
import org.junit.Test;

import com.ic.persistence.serialize.SerializationFormat;
import com.ic.persistence.serialize.SerializationFormatException;

public class TestNegativeConfig {
	
	private SerializationFormat.Builder builder ;
	
	@Before
	public void setup() {
		builder = SerializationFormat.create() ;
	}
	
	@Test(expected=SerializationFormatException.class)
	public void testDate() throws SerializationFormatException {
		builder.setDateFormat("Bad Format") ;
	}
	
	@Test(expected=SerializationFormatException.class)
	public void testDateEmpty() throws SerializationFormatException {
		builder.setDateFormat("") ;
	}
	
	@Test(expected=SerializationFormatException.class)
	public void testDate2() throws SerializationFormatException {
		builder.setDateFormat(null) ;
	}
	
	@Test(expected=SerializationFormatException.class)
	public void testValueSeperator() throws SerializationFormatException {
		builder.setValueSepartor("") ;
	}
	
	@Test(expected=SerializationFormatException.class)
	public void testValueSeperator2() throws SerializationFormatException {
		builder.setValueSepartor(null) ;
	}
	
	@Test(expected=SerializationFormatException.class)
	public void testObjectSeperator() throws SerializationFormatException {
		builder.setObjectSepartor("") ;
	}
	
	@Test(expected=SerializationFormatException.class)
	public void testObjectSeperator2() throws SerializationFormatException {
		builder.setObjectSepartor(null) ;
	}
	
	

}
