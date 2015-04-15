package com.ic.persistence.pipe;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Iterator;

public class PipeUtils_Test {

	public static String slurpReader(Reader reader, int bufferSize) throws IOException {
		try (StringWriter writer = new StringWriter()) {
			char[] buffer = new char[bufferSize];
			int len = -1;
			while ((len = reader.read(buffer, 0, bufferSize)) > 0) {
				writer.write(buffer, 0, len);
			}
			return writer.toString() ;
		}
	}

	
	
	public static Iterator<String> createStringIterator(String s) {
		return new StringIterator(s) ;
	}
	
	public static class StringIterator implements Iterator<String> {

		private String str;
		private int index = 0;

		public StringIterator(String str) {
			super();
			this.str = str;
		}

		@Override
		public boolean hasNext() {
			return index < str.length();
		}

		@Override
		public String next() {
			int endIndex = index + 1;
			return str.substring(index++, endIndex);
		}

		@Override
		public void remove() {
		}
	}

	
}
