package com.ic.persistence.serialize;

import java.text.SimpleDateFormat;

/**
 * Defines Serialization Configuration.
 * 
 * @author HTeunissen
 * 
 */
public class SerializationFormat {

	public static SerializationFormat CSV = create().build();

	public static Builder create() {
		return new Builder();
	}

	/**
	 * 
	 * Builder for FormatCongiuration;
	 * 
	 */
	public static class Builder {

		private transient SerializationFormat config = new SerializationFormat();

		public Builder setValueSepartor(String seperator)
				throws SerializationFormatException {

			if (!validSeparator(seperator)) {
				throw new SerializationFormatException(
						"Inavlid Value seperator " + seperator);
			}

			config.valueSeparator = seperator;
			return this;
		}

		public Builder setObjectSepartor(String seperator)
				throws SerializationFormatException {

			if (!validSeparator(seperator)) {
				throw new SerializationFormatException(
						"Inavlid Object seperator " + seperator);
			}

			config.objectSeperator = seperator;
			return this;
		}

		public Builder setNullValue(String nullValue)
				throws SerializationFormatException {

			if (nullValue == null) {
				throw new SerializationFormatException("Inavlid NullValue "
						+ nullValue);
			}

			config.nullValue = nullValue;
			return this;
		}

		public Builder setQuoteChar(char c) {
			config.quoteChar = c;
			return this;
		}

		public Builder setDateFormat(String dateFormat)
				throws SerializationFormatException {

			if( dateFormat == null || dateFormat.equals("") ) {
				throw new SerializationFormatException("Date Format not defined") ;
			}
			
			try {
				new SimpleDateFormat(dateFormat);
			} catch (Throwable e) {
				throw new SerializationFormatException("Inavlid Date format "
						+ config.dateFormat);
			}

			config.dateFormat = dateFormat;
			return this;
		}

		public Builder setEscapeQuotes(boolean escapeQuotes) {
			config.escapeQuotes = escapeQuotes;
			return this;
		}

		public Builder setBooleanValues(String booleanTrueValue,
				String booleanFalseValue) throws SerializationFormatException {
			config.booleanTrue = booleanTrueValue;
			config.booleanFalse = booleanFalseValue;
			return this;
		}

		public SerializationFormat build() {
			return config;
		}

		private boolean validSeparator(String s) {
			return s != null && !s.equals("");
		}

	}

	private String dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ"; // ISO_8601
	private String valueSeparator = ",";
	private String objectSeperator = "\n";
	private String nullValue = "";
	private String booleanTrue = "true";
	private String booleanFalse = "false";
	private char quoteChar = '"';
	private boolean escapeQuotes = true;

	private SerializationFormat() {
	}

	/**
	 * 
	 * @return Date Formatter
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	public String getValueSeparator() {
		return valueSeparator;
	}

	public String getObjectSeperator() {
		return objectSeperator;
	}

	public String getNullValue() {
		return nullValue;
	}

	public String getBooleanTrue() {
		return booleanTrue;
	}

	public String getBooleanFalse() {
		return booleanFalse;
	}

	public boolean isEscapeQuotes() {
		return escapeQuotes;
	}

	public char getQuoteChar() {
		return quoteChar;
	}

}
