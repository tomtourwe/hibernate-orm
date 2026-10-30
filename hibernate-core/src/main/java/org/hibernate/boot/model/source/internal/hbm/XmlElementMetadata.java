package org.hibernate.boot.model.source.internal.hbm;

import java.util.Locale;

/**
 * Provides meta-information about XML elements.
 *
 * @author Steve Ebersole
 */
public enum XmlElementMetadata {
	/**
	 * Describes the {@code <id/>} element
	 */
	ID( true ),
	/**
	 * Describes the {@code <composite-id/>} element
	 */
	COMPOSITE_ID( true ),
	/**
	 * Describes the {@code <discriminator/>} element
	 */
	DISCRIMINATOR( false ),
	/**
	 * Describes the {@code <multi-tenancy/>} element
	 */
	MULTI_TENANCY( false ),
	/**
	 * Describes the {@code <version/>} element
	 */
	VERSION( true ),
	/**
	 * Describes the {@code <timestamp/>} element
	 */
	TIMESTAMP( true ),
	/**
	 * Describes the {@code <properties/>} element
	 */
	PROPERTIES( true ),
	/**
	 * Describes the {@code <property/>} element
	 */
	PROPERTY( true ),
	/**
	 * Describes the {@code <key-property/>} element
	 */
	KEY_PROPERTY( true ),
	/**
	 * Describes the {@code <many-to-one/>} element
	 */
	MANY_TO_ONE( true ),
	/**
	 * Describes the {@code <key-many-to-one/>} element
	 */
	KEY_MANY_TO_ONE( true ),
	/**
	 * Describes the {@code <one-to-one/>} element
	 */
	ONE_TO_ONE( true ),
	/**
	 * Describes the {@code <any/>} element
	 */
	ANY( true ),
	/**
	 * Describes the {@code <component/>} element
	 */
	COMPONENT( true ),
	/**
	 * Describes the {@code <key/>} element
	 */
	KEY( false ),
	/**
	 * Describes the {@code <set/>} element
	 */
	SET( true ),
	/**
	 * Describes the {@code <list/>} element
	 */
	LIST( true ),
	/**
	 * Describes the {@code <bag/>} element
	 */
	BAG( true ),
	/**
	 * Describes the {@code <id-bag/>} element
	 */
	ID_BAG( true ),
	/**
	 * Describes the {@code <map/>} element
	 */
	MAP( true ),
	/**
	 * Describes the {@code <array/>} element
	 */
	ARRAY( true ),
	/**
	 * Describes the {@code <primitive-array/>} element
	 */
	PRIMITIVE_ARRAY( true ),
	/**
	 * Describes the {@code <collection-id/>} element
	 */
	COLLECTION_ID( false ),
	/**
	 * Describes the {@code <element/>} element
	 */
	ELEMENT( false ),
	/**
	 * Describes the {@code <many-to-many/>} element
	 */
	MANY_TO_MANY( false ),
	/**
	 * Describes the {@code <many-to-any/>} element
	 */
	MANY_TO_ANY( false ),
	/**
	 * Describes the {@code <map-key/>} element
	 */
	MAP_KEY( false ),
	/**
	 * Describes the {@code <map-key-many-to-many/>} element
	 */
	MAP_KEY_MANY_TO_MANY( false ),

	/**
	 * Describes the {@code <index/>} element
	 */
	INDEX( false ),
	/**
	 * Describes the {@code <index-many-to-many/>} element
	 */
	INDEX_MANY_TO_MANY( false ),
	/**
	 * Describes the {@code <list-index/>} element
	 */
	LIST_INDEX( false );
	private final boolean canBeNamed;

	XmlElementMetadata(boolean canBeNamed) {
		this.canBeNamed = canBeNamed;
	}

	/**
	 * The corresponding {@code hbm.xml} element name.  Used in error reporting
	 *
	 * @return The {@code hbm.xml} element name
	 */
	public String getElementName() {
		return name().toLowerCase(Locale.ROOT);
	}

	/**
	 * Can the source be named.  This is used in implicit naming (naming strategy).
	 *
	 * @return {@code true} indicates that the source can be named and therefore
	 * the column (assuming just one) is eligible for implicit naming.
	 */
	public boolean canBeNamed() {
		return canBeNamed;
	}
}
