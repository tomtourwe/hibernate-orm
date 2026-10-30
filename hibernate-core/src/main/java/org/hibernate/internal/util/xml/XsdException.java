package org.hibernate.internal.util.xml;

import org.hibernate.HibernateException;

/**
 * Indicates an issue finding or loading an XSD schema.
 *
 * @author Steve Ebersole
 */
public class XsdException extends HibernateException {
	public XsdException(String message) {
		super( message );
	}

	public XsdException(String message, Throwable root) {
		super( message, root );
	}
}
