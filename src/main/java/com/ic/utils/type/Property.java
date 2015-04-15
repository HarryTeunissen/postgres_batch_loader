package com.ic.utils.type;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Property implements Comparable<Property> {
	private String propertyName ;
	private Method getterMethod ;
	private Method setterMethod ;
	
	
	public Property(String propertyName, Method getterMethod,
			Method setterMethod) {
		super();
		this.propertyName = propertyName;
		this.getterMethod = getterMethod;
		this.setterMethod = setterMethod;
	}
	
	public boolean isReadOnly() {
		return setterMethod == null ;
	}
	
	public Object getValue(Object bean) throws Exception {
		return getterMethod.invoke(bean) ;
	}
	
	public void setValue(Object bean, Object val) throws Exception {
		setterMethod.invoke(bean, val) ;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Method getGetterMethod() {
		return getterMethod;
	}
	
	public <T extends Annotation> T getAnnotation(Class<T> clz) {
		return getterMethod.getAnnotation(clz) ;
	}

	public Method getSetterMethod() {
		return setterMethod;
	}
	
	
	public Class<?> getPropertyClass() {
		return getterMethod.getReturnType() ;
	}
	
	public Type getPropertyType() {
		return getterMethod.getGenericReturnType() ;
	}
	
	@Override
	public int compareTo(Property o) {
		return propertyName.compareTo(o.getPropertyName()) ;
	}

}
