package com.ic.persistence.serialize.property;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.ic.persistence.serialize.object.ObjectWriter;
import com.ic.persistence.serialize.property.impl.PropertyWriterServiceImpl;
import com.ic.utils.type.PropertyAccessor;

public class TestPropertyWriterService {
	
	private PropertyWriterService svc ;
	
	@Before
	public void setup() {
		svc = Guice.createInjector(new AbstractModule(){
			@Override
			protected void configure() {
				bind(PropertyWriterService.class).to(PropertyWriterServiceImpl.class) ;
			}
		}).getInstance(PropertyWriterService.class) ;
	}
	
	@Test
	public void testNumber() throws Exception {
		verifyNumber(createWriter(10), 10) ;
		verifyNumber(createWriter(10L), 10L) ;
		verifyNumber(createWriter(10.0), 10.0) ;
		verifyNumber(createWriter(10.0F), 10.0F) ;
	}
	
	@Test
	public void testString() throws Exception{
		verifyString(createWriter("Test"), "Test") ;
	}
	
	@Test
	public void testBoolean() throws Exception{
		verifyBoolean(createWriter(Boolean.TRUE), Boolean.TRUE) ;
		verifyBoolean(createWriter(true), Boolean.TRUE) ;
	}
	
	@Test
	public void testObject() throws Exception{
		Object val = new Object() ;
		verifyObject(createWriter(val), val) ;
	}
	
	@Test
	public void testDate() throws Exception {
		Date date = new Date() ;
		verifyDate(createWriter(date), date) ;
		
		java.sql.Date date2 = new java.sql.Date(date.getTime()) ;
		verifyDate(createWriter(date2), date2) ;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	private <V> PropertyWriter<Object> createWriter(V value) throws Exception {
		PropertyAccessor<Object, V>  pa = mock(PropertyAccessor.class) ;
		doReturn(value).when(pa).getValue(anyObject()) ;
		doReturn(value.getClass()).when(pa).getAccessorType() ;
		return svc.createPropertyWriter(pa) ;
	}
	
	private <V extends Number> void verifyNumber(PropertyWriter<Object> pw, V val) throws Exception {
		ObjectWriter  ow = mock(ObjectWriter.class) ;
		pw.write(null, ow);
		verify(ow).write(val);
	}
	
	private void verifyString(PropertyWriter<Object> pw, String val) throws Exception {
		ObjectWriter  ow = mock(ObjectWriter.class) ;
		pw.write(null, ow);
		verify(ow).write(val);
	}
	
	private void verifyBoolean(PropertyWriter<Object> pw, Boolean val) throws Exception {
		ObjectWriter  ow = mock(ObjectWriter.class) ;
		pw.write(null, ow);
		verify(ow).write(val);
	}
	
	private void verifyDate(PropertyWriter<Object> pw, Date val) throws Exception {
		ObjectWriter  ow = mock(ObjectWriter.class) ;
		pw.write(null, ow);
		verify(ow).write(val);
	}
	
	private void verifyObject(PropertyWriter<Object> pw, Object val) throws Exception {
		ObjectWriter  ow = mock(ObjectWriter.class) ;
		pw.write(null, ow);
		verify(ow).write(val.toString());
	}
	
}
