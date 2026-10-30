package org.hibernate.action.queue.internal.constraint;

/// Style of deferrability for database constraints.
///
/// @author Steve Ebersole
public enum Deferrability {
	/// The constraint check cannot be deferred; it is checked after every statement.
	///
	/// See [java.sql.DatabaseMetaData#importedKeyNotDeferrable].
	NOT_DEFERRABLE,

	/// The constraint is checked after each statement by default but can be deferred.
	///
	/// See [java.sql.DatabaseMetaData#importedKeyInitiallyImmediate].
	INITIALLY_IMMEDIATE,

	/// The constraint check is deferred until the end of the transaction by default.
	///
	/// See [java.sql.DatabaseMetaData#importedKeyInitiallyDeferred].
	INITIALLY_DEFERRED;

	public boolean isDeferrable() {
		return this == INITIALLY_IMMEDIATE || this == INITIALLY_DEFERRED;
	}
}
