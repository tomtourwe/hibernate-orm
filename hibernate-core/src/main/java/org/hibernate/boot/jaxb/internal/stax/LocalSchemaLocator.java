package org.hibernate.boot.jaxb.internal.stax;

import java.net.URL;

/**
 * Helper for resolving XML Schema references locally.
 *
 * @implNote *By design* we always use our ClassLoader to perform the lookups here.
 *
 * @author Steve Ebersole
 */
public class LocalSchemaLocator {

	private LocalSchemaLocator() {
		// Disallow direct instantiation
	}

	/**
	 * Given the resource name of a schema, locate its URL reference via ClassLoader lookup.
	 *
	 * @param schemaResourceName The local resource name to the schema
	 *
	 */
	public static URL resolveLocalSchemaUrl(String schemaResourceName) {
		final URL url = LocalSchemaLocator.class.getClassLoader().getResource( schemaResourceName );
		if ( url == null ) {
			throw new XmlInfrastructureException( "Unable to locate schema [" + schemaResourceName + "] via classpath" );
		}
		return url;
	}
}
