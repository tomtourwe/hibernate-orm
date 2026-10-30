package org.hibernate.internal.log;

import org.hibernate.spi.NavigablePath;

/**
 * Helper for logging collection, entity and embeddable information.  Uses path collapsing
 * for readability
 *
 * @author Steve Ebersole
 */
public class LoggingHelper {

	public static String toLoggableString(NavigablePath path) {
		assert path != null;

		return path.getFullPath();
	}
}
