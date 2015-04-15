package com.ic.utils.type;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TypeMapper {
	
public static TypeMapper MAPPER = new TypeMapper();

	public <T> Map<String, Property> createPropertyMap(Class<T> clz) {
		return getPropertyMap(clz) ;
	}
	
	public Property getProperty(Class<?> clz, String propertyName) {
		String prefix = "get" ;
		Method m = getGetterMethod(clz, prefix, propertyName) ;
		
		if( m == null ) {
			m = getGetterMethod(clz, prefix = "is", propertyName) ;
		}
		return m == null ? null : createProperty(clz, m, prefix, true) ;
	}
	
	private Method getGetterMethod(Class<?> clz, String prefix, String propertyName) {
		try {
			
			String fullName = prefix + propertyName.substring(0,1).toUpperCase() + propertyName.substring(1) ;
			
			return  clz.getMethod(fullName) ;
		} catch( Throwable err ) {
		}
		
		return null ;
	}
	
	public Map<String, Property> getPropertyMap(Class<?> clz) {
		return getPropertyMap(clz, false);
	}

	public Map<String, Property> getPropertyMap(Class<?> clz,
			boolean checkForReadOnly) {
		Map<String, Property> result = new HashMap<String, Property>();

		for (Method m : clz.getMethods()) {
			if (m.getParameterTypes().length == 0) {
				Property p = null;
				if (m.getName().startsWith("get")) {
					p = createProperty(clz, m, "get", checkForReadOnly);
				} else if (m.getName().startsWith("is")) {
					p = createProperty(clz, m, "is", checkForReadOnly);
				}
				if (p != null) {
					result.put(p.getPropertyName(), p);
				}
			}
		}

		return result;
	}

	private Property createProperty(Class<?> defineClass, Method getter,
			String prefix, boolean checkForReadOnly) {
		String n = getter.getName();
		String rawName = n.substring(prefix.length());
		String setterName = "set" + rawName;

		if (!getter.getDeclaringClass().equals(defineClass)) {
			return null;
		}

		try {
			Method setter = getter.getDeclaringClass().getMethod(setterName,
					getter.getReturnType());
			return new Property(toPropertyName(rawName), getter, setter);
		} catch (Exception err) {
			return checkForReadOnly ? new Property(toPropertyName(rawName),
					getter, null) : null;
		}
	}

	private static String toPropertyName(String rawName) {
		return rawName.substring(0, 1).toLowerCase() + rawName.substring(1);
	}
	
	
}
