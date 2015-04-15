package com.ic.persistence.strategy;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.junit.Test;

import com.ic.persistence.strategy.impl.UpdateBatchCommand;

public class TestBatchCommand {

	@Test
	public void test() throws SQLException, IOException {

		String sql = "SQL COMMAND";

		BatchCommand<Object, Connection> cmd = new UpdateBatchCommand<>(sql);

		Connection connection = mock(Connection.class);
		Statement stm = mock(Statement.class);
		doReturn(stm).when(connection).createStatement();

		cmd.execute(connection, new BatchCommandContext<Object>() {
			@Override
			public Iterator<Object> iterator() {
				return null;
			}
		});

		verify(stm).executeUpdate(sql);

	}

}
