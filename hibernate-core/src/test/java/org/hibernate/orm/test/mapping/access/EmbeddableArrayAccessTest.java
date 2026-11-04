package org.hibernate.orm.test.mapping.access;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.boot.MetadataSources;
import org.hibernate.mapping.AggregateColumn;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Value;
import org.hibernate.type.SqlTypes;
import org.hibernate.testing.orm.junit.DialectFeatureChecks;
import org.hibernate.testing.orm.junit.Jira;
import org.hibernate.testing.orm.junit.RequiresDialectFeature;
import org.hibernate.testing.orm.junit.ServiceRegistry;
import org.hibernate.testing.orm.junit.ServiceRegistryScope;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/// An embeddable array or list (an aggregate) takes the access of its containing class,
/// independently of an access override on the embedding attribute, as a singular embeddable does.
@ServiceRegistry
@RequiresDialectFeature(feature = DialectFeatureChecks.SupportsJsonAggregate.class)
@Jira( "https://hibernate.atlassian.net/browse/HHH-21017" )
class EmbeddableArrayAccessTest {

	@Test
	void singularEmbeddable(ServiceRegistryScope scope) {
		var detail = entity( scope, Owner.class ).getProperty( "detail" );
		assertThat( detail.getPropertyAccessorName() ).isEqualTo( "property" );
		assertThat( description( detail ).getPropertyAccessorName() ).isEqualTo( "field" );
	}

	@Test
	void embeddableArray(ServiceRegistryScope scope) {
		var details = entity( scope, Owner.class ).getProperty( "details" );
		assertThat( details.getPropertyAccessorName() ).isEqualTo( "property" );
		assertThat( description( details ).getPropertyAccessorName() ).isEqualTo( "field" );
	}

	@Test
	void embeddableArrayWithoutAccessors(ServiceRegistryScope scope) {
		var details = entity( scope, PlainOwner.class ).getProperty( "details" );
		assertThat( description( details ).getPropertyAccessorName() ).isEqualTo( "field" );
	}

	private static PersistentClass entity(ServiceRegistryScope scope, Class<?> type) {
		return new MetadataSources( scope.getRegistry() )
				.addAnnotatedClass( type )
				.buildMetadata()
				.getEntityBinding( type.getName() );
	}

	private static Property description(Property attribute) {
		return component( attribute.getValue() ).getProperty( "description" );
	}

	private static Component component(Value value) {
		if ( value instanceof Component component ) {
			return component;
		}
		// an embeddable array is a basic value whose single column is the aggregate column of the embeddable
		return ( (AggregateColumn) value.getColumns().get( 0 ) ).getComponent();
	}

	@Entity(name = "Owner")
	@Access( AccessType.FIELD )
	static class Owner {
		@Id
		long id;
		@Transient
		Detail detail;
		@Transient
		Detail[] details;

		@Embedded
		@Access( AccessType.PROPERTY )
		public Detail getDetail() {
			return detail;
		}

		public void setDetail(Detail detail) {
			this.detail = detail;
		}

		@Embedded
		@Access( AccessType.PROPERTY )
		@JdbcTypeCode( SqlTypes.JSON_ARRAY )
		public Detail[] getDetails() {
			return details;
		}

		public void setDetails(Detail[] details) {
			this.details = details;
		}
	}

	@Embeddable
	static class Detail {
		String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}

	@Entity(name = "PlainOwner")
	@Access( AccessType.FIELD )
	static class PlainOwner {
		@Id
		long id;
		@Transient
		Plain[] details;

		@Embedded
		@Access( AccessType.PROPERTY )
		@JdbcTypeCode( SqlTypes.JSON_ARRAY )
		public Plain[] getDetails() {
			return details;
		}

		public void setDetails(Plain[] details) {
			this.details = details;
		}
	}

	/// Mapped by its field only, without getters and setters.
	@Embeddable
	static class Plain {
		String description;
	}
}
