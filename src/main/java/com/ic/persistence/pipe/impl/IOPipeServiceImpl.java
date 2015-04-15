package com.ic.persistence.pipe.impl;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.ic.persistence.pipe.IOPipeService;
import com.ic.persistence.pipe.ObjectSerializerFactory;
import com.ic.persistence.serialize.ObjectSerializer;

/**
 * 
 * @author HTeunissen
 *
 */
public class IOPipeServiceImpl implements IOPipeService {

	private static Logger log = LoggerFactory.getLogger(IOPipeServiceImpl.class
			.getName());

	private final static int DEFAULT_BUFFER_SIZE = 1024;
	private ExecutorService executorService;

	@Inject
	public IOPipeServiceImpl() {
		super();
		this.executorService = Executors.newFixedThreadPool(10);
	}

	@Override
	public <T> Reader createReader(Iterator<T> itr,
			ObjectSerializerFactory<T> serializerFactory) throws IOException {
		return createReader(DEFAULT_BUFFER_SIZE, itr, serializerFactory);
	}

	public <T> Reader createReader(int bufferSize, final Iterator<T> itr,
			ObjectSerializerFactory<T> serializerFactory) throws IOException {

		final PipedWriter writer = new PipedWriter();
		final MonitoredPipeReader reader = new MonitoredPipeReader(writer, bufferSize);

		final ObjectSerializer<T> serializer = serializerFactory.create(writer);

		executorService.submit(new Runnable() {
			@Override
			public void run() {

				try {
					while (itr.hasNext()) {
						serializer.write(itr.next());
					}
				} catch (Throwable err) {
					reader.setWriteExcepton(err);
				} finally {
					try {
						writer.close();
					} catch (IOException err) {
						log.error(err.getMessage(), err);
					}
				}
			}
		});

		return reader;
	}

	/**
	 * MonitoredPipeReader will throw IOException on close if any exceptions encountered 
	 * during runtime processing on background Thread
	 * @author HTeunissen
	 *
	 */
	private static class MonitoredPipeReader extends PipedReader {

		private AtomicReference<IOException> exceptionRef = new AtomicReference<>();
		
		
		public MonitoredPipeReader(PipedWriter src, int pipeSize)
				throws IOException {
			super(src, pipeSize);
		}

		
		public void setWriteExcepton(Throwable err) {
			exceptionRef.set((err instanceof IOException) ? (IOException) err
					: new IOException(err.getMessage(), err));
		}

		@Override
		public void close() throws IOException {
			super.close();
			IOException exception = exceptionRef.get();
			if (exception != null) {
				throw exception;
			}
		}

	}

}
