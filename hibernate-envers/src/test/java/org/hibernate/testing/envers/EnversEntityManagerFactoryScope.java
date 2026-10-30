package org.hibernate.testing.envers;

import jakarta.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.strategy.spi.AuditStrategy;

/**
 * @author Chris Cranford
 */
public class EnversEntityManagerFactoryScope {
	private final EnversEntityManagerFactoryProducer entityManagerFactoryProducer;
	private final Strategy auditStrategy;

	public EnversEntityManagerFactoryScope(EnversEntityManagerFactoryProducer producer, Strategy auditStrategy) {
		this.auditStrategy = auditStrategy;
		this.entityManagerFactoryProducer = producer;
	}

	public static AuditStrategy getAuditStrategy(EntityManager entityManager) {
		if ( entityManager.getDelegate() instanceof Session ) {
			return getAuditStrategy( (Session) entityManager.getDelegate() );
		}
		else if ( entityManager.getDelegate() instanceof EntityManager ) {
			return getAuditStrategy( (EntityManager) entityManager.getDelegate() );
		}
		else {
			throw new IllegalArgumentException( "Could not resolve AuditStrategy" );
		}
	}

	public static AuditStrategy getAuditStrategy(Session session) {
		SessionImplementor sessionImpl;
		if ( !(session instanceof SessionImplementor) ) {
			sessionImpl = (SessionImplementor) session.getSessionFactory().getCurrentSession();
		}
		else {
			sessionImpl = (SessionImplementor) session;
		}
		final var enversService = sessionImpl.getFactory()
				.getServiceRegistry()
				.getService( EnversService.class );
		if ( enversService == null ) {
			throw new IllegalArgumentException( "EnversService is not available in the provided Session" );
		}
		return enversService.getAuditStrategy();
	}
}
