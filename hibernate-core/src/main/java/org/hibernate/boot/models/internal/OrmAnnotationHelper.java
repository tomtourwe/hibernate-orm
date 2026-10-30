package org.hibernate.boot.models.internal;

import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.function.Consumer;

import org.hibernate.boot.models.DialectOverrideAnnotations;
import org.hibernate.boot.models.HibernateAnnotations;
import org.hibernate.boot.models.JpaAnnotations;
import org.hibernate.boot.models.XmlAnnotations;
import org.hibernate.models.AnnotationAccessException;
import org.hibernate.models.spi.AnnotationDescriptor;
import org.hibernate.models.spi.AttributeDescriptor;
import org.hibernate.models.spi.ModelsContext;


/**
 * @author Steve Ebersole
 */
public class OrmAnnotationHelper {

	public static void forEachOrmAnnotation(Consumer<AnnotationDescriptor<?>> consumer) {
		JpaAnnotations.forEachAnnotation( consumer );
		HibernateAnnotations.forEachAnnotation( consumer );
		DialectOverrideAnnotations.forEachAnnotation( consumer );
		XmlAnnotations.forEachAnnotation( consumer );
	}

	public static void forEachOrmAnnotation(Class<?> declarer, Consumer<AnnotationDescriptor<?>> consumer) {
		for ( var field : declarer.getFields() ) {
			if ( AnnotationDescriptor.class.isAssignableFrom( field.getType() ) ) {
				try {
					consumer.accept( (AnnotationDescriptor<?>) field.get( null ) );
				}
				catch (IllegalAccessException e) {
					throw new AnnotationAccessException(
							String.format(
									Locale.ROOT,
									"Unable to access standard annotation descriptor field - %s",
									field.getName()
							),
							e
					);
				}
			}
		}
	}

	public static <V, A extends Annotation> V extractJdkValue(A jdkAnnotation, AttributeDescriptor<V> attributeDescriptor, ModelsContext modelContext) {
		return attributeDescriptor.getTypeDescriptor()
				.createJdkValueExtractor( modelContext )
				.extractValue( jdkAnnotation, attributeDescriptor, modelContext );
	}

	public static <V, A extends Annotation> V extractJdkValue(A jdkAnnotation, AnnotationDescriptor<A> annotationDescriptor, String attributeName, ModelsContext modelContext) {
		final AttributeDescriptor<V> attributeDescriptor = annotationDescriptor.getAttribute( attributeName );
		return extractJdkValue( jdkAnnotation, attributeDescriptor, modelContext );
	}
}
