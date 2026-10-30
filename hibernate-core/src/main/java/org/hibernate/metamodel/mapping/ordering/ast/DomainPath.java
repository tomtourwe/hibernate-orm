package org.hibernate.metamodel.mapping.ordering.ast;


import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.spi.NavigablePath;

/**
 * Represents a domain-path (model part path) used in an order-by fragment
 *
 * @author Steve Ebersole
 */
public interface DomainPath extends OrderingExpression, SequencePart {
	NavigablePath getNavigablePath();

	ModelPart getReferenceModelPart();

	@Override
	default String toDescriptiveText() {
		return "domain path (" + getNavigablePath().getFullPath() + ")";
	}
}
