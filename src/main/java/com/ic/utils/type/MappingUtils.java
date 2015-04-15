package com.ic.utils.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ic.utils.transform.Filter;
import com.ic.utils.transform.Transform;

public class MappingUtils {

	public static <S, D> Map<D,S> hash(Collection<S> src, Transform<S,D> converter) {
		Map<D, S> result = new HashMap<D, S>(src.size()) ;
		
		for(S s : src) {
			result.put(converter.converter(s), s) ;
		}
		
		return result ;
	}
	
	public static <S extends Comparable<S>> List<S> sort(Collection<S> source) {
		List<S> r = new ArrayList<S>(source);
		Collections.sort(r);
		return r;
	}
	
	public static <T> T findFirst(Collection<T> src, Filter<T> filter) {
		for(T v : src) {
			if( filter.isMember(v) ) {
				return v;
			}
		}
		return null ;
	}
	
	
	public static <T> List<T> filter(Collection<T> src, Filter<T> filter) {
		List<T> result = new ArrayList<>(src.size()) ;
		
		for(T v : src) {
			if( filter.isMember(v) ) {
				result.add(v) ;
			}
		}
		
		return result;
	}
	
	public static <S, D> List<D> transform(Collection<S> src, Transform<S,D> converter) {
		List<D> result = new ArrayList<>(src.size()) ;
		for(S s : src) {
			result.add(converter.converter(s)) ;
		}
		return result ;
	}
	
	public static <S, D> List<D> transform(S[] src, Transform<S,D> converter) {
		List<D> result = new ArrayList<>(src.length) ;
		for(S s : src) {
			result.add(converter.converter(s)) ;
		}
		return result ;
	}
	
	public static <S, D> List<D> transformNullFilter(Collection<S> src, Transform<S,D> converter) {
		List<D> result = new ArrayList<>(src.size()) ;
		for(S s : src) {
			D d = converter.converter(s) ;
			if( d != null ) {
				result.add(converter.converter(s)) ;
			}
		}
		return result ;
	}
	
	
	public static <T> T extractScalar(Collection<T> reports) {
		return reports.size() == 0 ? null : reports.iterator().next() ;
	}
	
	public static <T> List<T> toList(T element) {
		List<T> list = new ArrayList<T>(1) ;
		list.add(element) ;
		return list ;
	}
	
	public static Transform<String, Long> LongConverter = new Transform<String, Long>() {
		@Override
		public Long converter(String src) {
			return Long.parseLong(src) ;
		}
	}; 
	
	
	public static List<Long> toLongList(String[] list) {
		if( list == null ) {
			return new ArrayList<>() ;
		}
		return transform(list, LongConverter) ;
	
	}
	
	public static List<String> toList(String[] array) {
		
		if( array == null ) {
			return new ArrayList<>() ;
		}
		
		List<String> result = new ArrayList<>(array.length) ;
		for(String s : array) {
			result.add(s) ;
		}
		return result ;
	}

	
}
