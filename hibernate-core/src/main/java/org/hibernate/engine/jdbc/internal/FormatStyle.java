package org.hibernate.engine.jdbc.internal;

/**
 * Represents the understood types or styles of formatting.
 *
 * @author Steve Ebersole
 */
public enum FormatStyle {
	/**
	 * Formatting for SELECT, INSERT, UPDATE and DELETE statements
	 */
	BASIC( AntlrBasedSQLFormatterImpl.INSTANCE ),
	/**
	 * Formatting for DDL (CREATE, ALTER, DROP, etc) statements
	 */
	DDL( DDLFormatterImpl.INSTANCE ),
	/**
	 * Syntax highlighting via ANSI escape codes
	 */
	HIGHLIGHT( HighlightingFormatter.INSTANCE ),
	/**
	 * No formatting
	 */
	NONE( NoFormatImpl.INSTANCE );
	private final Formatter formatter;

	FormatStyle(Formatter formatter) {
		this.formatter = formatter;
	}

	public Formatter getFormatter() {
		return formatter;
	}

	private static class NoFormatImpl implements Formatter {
		/**
		 * Singleton access
		 */
		public static final NoFormatImpl INSTANCE = new NoFormatImpl();

		public String format(String source) {
			return source;
		}
	}
}
