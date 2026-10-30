package org.hibernate.action.internal;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.collection.spi.CollectionMutationInterpretation;
import org.hibernate.engine.internal.FlushProcessingContext;
import org.hibernate.event.spi.EventSource;
import org.hibernate.event.spi.PostCollectionRemoveEvent;
import org.hibernate.event.spi.PostCollectionRemoveEventListener;
import org.hibernate.event.spi.PreCollectionRemoveEvent;
import org.hibernate.event.spi.PreCollectionRemoveEventListener;
import org.hibernate.persister.collection.CollectionPersister;

/**
 * The action for removing a collection
 */
public final class CollectionRemoveAction extends CollectionAction {

	@Nullable
	private final Object affectedOwner;
	private final boolean emptySnapshot;

	/// Creates the legacy lowering of an already-prepared semantic mutation.
	///
	/// @since 8.0
	public CollectionRemoveAction(
			final @Nullable PersistentCollection<?> collection,
			final @Nonnull CollectionPersister persister,
			final @Nullable Object id,
			final boolean emptySnapshot,
			final @Nonnull EventSource session,
			final @Nullable Object affectedOwner,
			final @Nullable Object affectedOwnerId,
			final @Nullable CollectionMutationInterpretation interpretation) {
		super( persister, collection, id, session, true, interpretation );
		this.emptySnapshot = emptySnapshot;
		this.affectedOwner = affectedOwner;
	}

	@Override
	public void execute() {
		if ( !isLifecyclePrepared() && getCollection() != null ) {
			preRemove();
		}
		final var session = getSession();
		if ( !emptySnapshot ) {
			// an existing collection that was either nonempty or uninitialized
			// is replaced by null or a different collection
			// (if the collection is uninitialized, Hibernate has no way of
			// knowing if the collection is actually empty without querying the db)
			final var persister = getPersister();
			final Object key = getKey();
			assert key != null;
			final var eventMonitor = session.getEventMonitor();
			final var event = eventMonitor.beginCollectionRemoveEvent();
			boolean success = false;
			try {
				persister.remove( key, session );
				success = true;
			}
			finally {
				eventMonitor.completeCollectionRemoveEvent( event, key, persister.getRole(), success, session );
			}
		}

		final var collection = getCollection();
		if ( collection != null ) {
			session.getPersistenceContextInternal().getCollectionEntry( collection ).afterAction( collection );
			postRemove();
		}
		evict();
		final Runnable recordStatistics = () -> {
			final var statistics = session.getFactory().getStatistics();
			if ( statistics.isStatisticsEnabled() ) {
				statistics.removeCollection( getPersister().getRole() );
			}
		};
		final var tracker = session.getPersistenceContextInternal().getCollectionFlushActionTracker();
		if ( tracker instanceof FlushProcessingContext flushProcessingContext ) {
			flushProcessingContext.ownerCollectionMutationCompleted(
					affectedOwner,
					getPersister().isInverse(),
					recordStatistics
			);
		}
		else {
			recordStatistics.run();
		}
	}

	private void preRemove() {
		getEventListenerGroups().eventListenerGroup_PRE_COLLECTION_REMOVE
				.fireLazyEventOnEachListener( this::newPreCollectionRemoveEvent,
						PreCollectionRemoveEventListener::onPreRemoveCollection );
	}

	@Nonnull
	private PreCollectionRemoveEvent newPreCollectionRemoveEvent() {
		final var collection = getCollection();
		assert collection != null;
		return new PreCollectionRemoveEvent( getPersister(), collection, eventSource(), affectedOwner );
	}

	private void postRemove() {
		getEventListenerGroups().eventListenerGroup_POST_COLLECTION_REMOVE
				.fireLazyEventOnEachListener( this::newPostCollectionRemoveEvent,
						PostCollectionRemoveEventListener::onPostRemoveCollection );
	}

	@Nonnull
	private PostCollectionRemoveEvent newPostCollectionRemoveEvent() {
		final var collection = getCollection();
		assert collection != null;
		return new PostCollectionRemoveEvent( getPersister(), collection, eventSource(), affectedOwner );
	}

	@Nullable
	public Object getAffectedOwner() {
		return affectedOwner;
	}
}
