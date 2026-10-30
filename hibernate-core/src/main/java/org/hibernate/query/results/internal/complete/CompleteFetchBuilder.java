package org.hibernate.query.results.internal.complete;

import java.util.List;

import org.hibernate.query.results.spi.FetchBuilder;
import org.hibernate.sql.results.graph.Fetchable;

/**
 * @author Steve Ebersole
 */
public interface CompleteFetchBuilder extends FetchBuilder {
	Fetchable getReferencedPart();

	List<String> getColumnAliases();
}
