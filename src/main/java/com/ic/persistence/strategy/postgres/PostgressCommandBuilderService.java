package com.ic.persistence.strategy.postgres;

import com.ic.persistence.mapping.ObjectMapper;

/**
 * 
 * @author HTeunissen
 *
 */
public interface PostgressCommandBuilderService {

	public <T> PostgressCommandBuilder<T> createCommandBuilder(ObjectMapper<T> mapper) ;
	
}
