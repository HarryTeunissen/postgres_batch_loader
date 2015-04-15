package com.ic.persistence.strategy.postgres.impl;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import com.ic.persistence.mapping.ObjectMapper;
import com.ic.persistence.pipe.IOPipeService;
import com.ic.persistence.pipe.ObjectSerializerFactory;
import com.ic.persistence.serialize.ObjectSerializer;
import com.ic.persistence.serialize.ObjectSerializerService;
import com.ic.persistence.serialize.SerializationFormat;
import com.ic.persistence.strategy.BatchCommand;
import com.ic.persistence.strategy.BatchCommandContext;

/**
 * 
 * @author HTeunissen
 *
 * @param <T>
 */
public class CopyCommand<T> implements BatchCommand<T, BaseConnection> {

	private IOPipeService pipeService;
	private ObjectSerializerService serializerService;

	private String sql;
	private ObjectMapper<T> mapper;

	public CopyCommand(IOPipeService pipeService,
			ObjectSerializerService serializerService, String sql,
			ObjectMapper<T> mapper) {
		super();
		this.pipeService = pipeService;
		this.serializerService = serializerService;
		this.sql = sql;
		this.mapper = mapper;
	}

	@Override
	public void execute(BaseConnection connection, BatchCommandContext<T> ctx)
			throws SQLException, IOException {
		CopyManager copyManager = createCopyManager(connection);
		copyManager.copyIn(sql, pipeService.createReader(ctx.iterator(),
				new ObjectSerializerFactory<T>() {
					@Override
					public ObjectSerializer<T> create(Writer writer) {
						return serializerService.createObjectSerializer(
								mapper.getMappedProperties(), writer,
								SerializationFormat.CSV);
					}
				}));
	}
	
	protected CopyManager createCopyManager(BaseConnection connection) throws SQLException {
		return new CopyManager(connection) ;
	}

}
