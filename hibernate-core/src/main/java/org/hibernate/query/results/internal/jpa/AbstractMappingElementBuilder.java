package org.hibernate.query.results.internal.jpa;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.descriptor.java.JavaType;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractMappingElementBuilder<T> implements MappingElementBuilder<T> {
	protected final JavaType<T> javaType;

	protected final SessionFactoryImplementor sessionFactory;

	public AbstractMappingElementBuilder(String alias, Class<T> javaType, SessionFactoryImplementor sessionFactory) {
		this( sessionFactory.getTypeConfiguration().getJavaTypeRegistry().resolveDescriptor( javaType ), sessionFactory );
	}

	public AbstractMappingElementBuilder(JavaType<T> javaType, SessionFactoryImplementor sessionFactory) {
		this.javaType = javaType;
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Class<T> getJavaType() {
		return javaType.getJavaTypeClass();
	}
}
