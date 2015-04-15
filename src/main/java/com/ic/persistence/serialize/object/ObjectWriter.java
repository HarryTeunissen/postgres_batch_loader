package com.ic.persistence.serialize.object;

import java.io.IOException;
import java.util.Date;

/**
 * ObjectWriter writes directly into io.stream
 * 
 * @author HTeunissen
 * 
 */
public interface ObjectWriter {

	/**
	 * 
	 * @throws IOException
	 */
	public void startObject() throws IOException;

	/**
	 * 
	 * @throws IOException
	 */
	public void endObject() throws IOException;

	/**
	 * 
	 * @throws IOException
	 */
	public void flush() throws IOException;

	/**
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException;

	/**
	 * 
	 * @param value
	 * @throws IOException
	 */
	public void write(Boolean value) throws IOException;

	/**
	 * 
	 * @param s
	 * @throws IOException
	 */
	public void write(String s) throws IOException;

	/**
	 * 
	 * @param date
	 * @throws IOException
	 */
	public void write(Date date) throws IOException;

	/**
	 * 
	 * @param number
	 * @throws IOException
	 */
	public void write(Number number) throws IOException;

	/**
	 * write Null Value into Stream
	 */
	public void writeNull() throws IOException;

}
