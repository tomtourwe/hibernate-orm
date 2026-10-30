package org.hibernate.engine.jdbc.env.internal;



/**
 * Enumeration of the JDBC LOB locator types
 *
 * @author Steve Ebersole
 */
public enum LobTypes {
	BLOB( ),
	CLOB( ),
	NCLOB( );

	LobTypes() {
	}
}
