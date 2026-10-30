package org.hibernate.boot.model.internal;

import jakarta.annotation.Nonnull;
import jakarta.persistence.QueryHint;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.hibernate.internal.util.collections.CollectionHelper.isEmpty;
import static org.hibernate.internal.util.collections.CollectionHelper.mapOfSize;

/**
 * @author Strong Liu
 */
public class QueryHintDefinition {
	private final Map<String, Object> hintsMap;

	public QueryHintDefinition(final QueryHint[] hints) {
		if ( isEmpty( hints ) ) {
			hintsMap = emptyMap();
		}
		else {
			final Map<String, Object> map = mapOfSize( hints.length );
			for ( var hint : hints ) {
				map.put( hint.name(), hint.value() );
			}
			hintsMap = map;
		}
	}


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Generic access

	@Nonnull
	public Map<String, Object> getHintsMap() {
		return hintsMap;
	}
}
