package org.hibernate.metamodel.mapping.ordering.ast;

import jakarta.annotation.Nonnull;

import org.hibernate.metamodel.mapping.CollectionPart;
import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.metamodel.mapping.ModelPartContainer;
import org.hibernate.metamodel.mapping.internal.AbstractDomainPath;
import org.hibernate.metamodel.mapping.ordering.TranslationContext;
import org.hibernate.spi.NavigablePath;

/**
 * Represents a part of a `CollectionPart` (element or index descriptor) as a DomainPath
 *
 * @author Steve Ebersole
 */
public class CollectionPartPath extends AbstractDomainPath {
	private final NavigablePath navigablePath;
	private final CollectionPart referencedPart;

	CollectionPartPath(
			PluralAttributePath lhs,
			CollectionPart referencedPart) {
		this.referencedPart = referencedPart;

		this.navigablePath = lhs.getNavigablePath().append( referencedPart.getPartName() );
	}

	@Override
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}

	@Override
	public CollectionPart getReferenceModelPart() {
		return referencedPart;
	}

	@Nonnull
	@Override
	public SequencePart resolvePathPart(
			String name,
			String identifier,
			boolean isTerminal,
			TranslationContext translationContext) {
		if ( referencedPart instanceof ModelPartContainer modelPartContainer ) {
			final ModelPart subPart = modelPartContainer.findSubPart( name, null );
			if ( subPart == null ) {
				throw new PathResolutionException( name );
			}
			return new DomainPathContinuation( navigablePath.append( name ), subPart );
		}
		else {
			throw new PathResolutionException( name );
		}
	}
}
