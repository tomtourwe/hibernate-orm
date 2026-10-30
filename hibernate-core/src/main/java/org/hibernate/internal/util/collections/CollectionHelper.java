package org.hibernate.internal.util.collections;

import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.addAll;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;

/**
 * Various helper util methods for handling collections.
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public final class CollectionHelper {
	public static final int DEFAULT_LIST_CAPACITY = 10;
	public static final int MINIMUM_INITIAL_CAPACITY = 16;
	public static final float LOAD_FACTOR = 0.75f;

	private CollectionHelper() {
	}

	/**
	 * Build a properly sized map, especially handling load size and load factor to prevent immediate resizing.
	 * <p>
	 * Especially helpful for copy map contents.
	 *
	 * @param size The size to make the map.
	 *
	 * @return The sized map.
	 */
	public static <K, V> HashMap<K, V> mapOfSize(int size) {
		return new HashMap<>( determineProperSizing( size ), LOAD_FACTOR );
	}

	/**
	 * Build a properly sized linked map, especially handling load size and load factor to prevent immediate resizing.
	 * <p>
	 * Especially helpful for copy map contents.
	 *
	 * @param size The size to make the map.
	 *
	 * @return The sized linked map.
	 */
	public static <K, V> LinkedHashMap<K, V> linkedMapOfSize(int size) {
		return new LinkedHashMap<>( determineProperSizing( size ), LOAD_FACTOR );
	}

	/**
	 * Build a map whose size is unknown.
	 *
	 * @return The map.
	 */
	public static <K, V> HashMap<K, V> map() {
		return new HashMap<>();
	}

	/**
	 * Build a linked map whose size is unknown.
	 *
	 * @return The linked map.
	 */
	public static <K, V> LinkedHashMap<K, V> linkedMap() {
		return new LinkedHashMap<>();
	}

	/**
	 * Build a properly sized set, especially handling load size and load factor to prevent immediate resizing.
	 * <p>
	 * Especially helpful for copy set contents.
	 *
	 * @param size The size to make the set.
	 *
	 * @return The sized set.
	 */
	public static <K> HashSet<K> setOfSize(int size) {
		return new HashSet<>( determineProperSizing( size ), LOAD_FACTOR );
	}

	/**
	 * Build a set whose size is unknown.
	 *
	 * @return The set.
	 */
	public static <K> HashSet<K> set() {
		return new HashSet<>();
	}

	/**
	 * Build a properly sized linked set, especially handling load size and load factor to prevent immediate resizing.
	 * <p>
	 * Especially helpful for copy set contents.
	 *
	 * @param size The size to make the set.
	 *
	 * @return The sized linked set.
	 */
	public static <K> LinkedHashSet<K> linkedSetOfSize(int size) {
		return new LinkedHashSet<>( determineProperSizing( size ), LOAD_FACTOR );
	}

	/**
	 * Build a linked set whose size is unknown.
	 *
	 * @return The linked set.
	 */
	public static <K> LinkedHashSet<K> linkedSet() {
		return new LinkedHashSet<>();
	}

	/**
	 * Given a map, determine the proper initial size for a new Map to hold the same number of values.
	 * Specifically we want to account for load size and load factor to prevent immediate resizing.
	 *
	 * @param original The original map
	 *
	 * @return The proper size.
	 */
	public static int determineProperSizing(Map<?,?> original) {
		return determineProperSizing( original.size() );
	}

	/**
	 * Determine the proper initial size for a new collection in order for it to hold the given a number of elements.
	 * Specifically we want to account for load size and load factor to prevent immediate resizing.
	 *
	 * @param numberOfElements The number of elements to be stored.
	 *
	 * @return The proper size.
	 */
	public static int determineProperSizing(int numberOfElements) {
		return Math.max( ( (int) ( numberOfElements / LOAD_FACTOR ) ) + 1, MINIMUM_INITIAL_CAPACITY );
	}

	/**
	 * Create a properly sized {@link ConcurrentHashMap} based on the given expected number of elements.
	 *
	 * @param expectedNumberOfElements The expected number of elements for the created map
	 * @param <K> The map key type
	 * @param <V> The map value type
	 *
	 * @return The created map.
	 */
	public static <K, V> ConcurrentHashMap<K, V> concurrentMap(int expectedNumberOfElements) {
		return concurrentMap( expectedNumberOfElements, LOAD_FACTOR );
	}

	/**
	 * Create a properly sized {@link ConcurrentHashMap} based on the given expected number of elements and an
	 * explicit load factor
	 *
	 * @param expectedNumberOfElements The expected number of elements for the created map
	 * @param loadFactor The collection load factor
	 * @param <K> The map key type
	 * @param <V> The map value type
	 *
	 * @return The created map.
	 */
	public static <K, V> ConcurrentHashMap<K, V> concurrentMap(int expectedNumberOfElements, float loadFactor) {
		return new ConcurrentHashMap<>( expectedNumberOfElements, loadFactor );
	}

	public static <T> ArrayList<T> arrayList(int expectedNumberOfElements) {
		return new ArrayList<>( Math.max( expectedNumberOfElements + 1, DEFAULT_LIST_CAPACITY ) );
	}

	public static <T> Set<T> makeCopy(Set<T> source) {
		if ( source == null ) {
			return null;
		}
		else {
			final Set<T> copy = setOfSize( source.size() + 1 );
			copy.addAll( source );
			return copy;
		}
	}

	public static boolean isEmpty(@Nullable Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isEmpty(@Nullable Map<?,?> map) {
		return map == null || map.isEmpty();
	}

	public static boolean isNotEmpty(@Nullable Collection<?> collection) {
		return !isEmpty( collection );
	}

	public static boolean isNotEmpty(@Nullable Map<?,?> map) {
		return !isEmpty( map );
	}

	public static boolean isEmpty(@Nullable Object[] objects) {
		return objects == null || objects.length == 0;
	}

	public static boolean isNotEmpty(@Nullable Object[] objects) {
		return objects != null && objects.length > 0;
	}

	public static <T> List<T> listOf(T value1) {
		final List<T> list = new ArrayList<>( 1 );
		list.add( value1 );
		return list;
	}

	@SafeVarargs
	public static <T> List<T> listOf(T... values) {
		final List<T> list = new ArrayList<>( values.length );
		addAll( list, values );
		return list;
	}

	@SafeVarargs
	public static <T> Set<T> setOf(T... values) {
		final HashSet<T> set = new HashSet<>( determineProperSizing( values.length ) );
		addAll( set, values );
		return set;
	}

	public static Properties asProperties(Map<?,?> map) {
		if ( map instanceof Properties properties ) {
			return properties;
		}
		else {
			final var properties = new Properties();
			if ( isNotEmpty( map ) ) {
				properties.putAll( map );
			}
			return properties;
		}
	}

	/**
	 * Use to convert sets which will be retained for a long time,
	 * such as for the lifetime of the Hibernate ORM instance.
	 * The returned Set might be immutable, but there is no guarantee of this:
	 * consider it immutable but don't rely on this.
	 * The goal is to save memory.
	 * @return will never return null, but might return an immutable collection.
	 */
	public static <T> Set<T> toSmallSet(Set<T> set) {
		return switch ( set.size() ) {
			case 0 -> emptySet();
			case 1 -> singleton( set.iterator().next() );
			//TODO assert tests pass even if this is set to return an unmodifiable Set
			default -> set;

		};
	}

	/**
	 * Use to convert Maps which will be retained for a long time,
	 * such as for the lifetime of the Hibernate ORM instance.
	 * The returned Map might be immutable, but there is no guarantee of this:
	 * consider it immutable but don't rely on this.
	 * The goal is to save memory.
	 */
	public static <K, V> Map<K, V> toSmallMap(final Map<K, V> map) {
		if ( map == null ) {
			return emptyMap();
		}
		return switch ( map.size() ) {
			case 0 -> emptyMap();
			case 1 -> {
				var entry = map.entrySet().iterator().next();
				yield singletonMap( entry.getKey(), entry.getValue() );
			}
			//TODO assert tests pass even if this is set to return an unmodifiable Map
			default -> map;

		};
	}

	/**
	 * Use to convert ArrayList instances which will be retained for a long time,
	 * such as for the lifetime of the Hibernate ORM instance.
	 * The returned List might be immutable, but there is no guarantee of this:
	 * consider it immutable but don't rely on this.
	 * The goal is to save memory.
	 */
	public static <V> List<V> toSmallList(ArrayList<V> arrayList) {
		if ( arrayList == null ) {
			return emptyList();
		}
		return switch ( arrayList.size() ) {
			case 0 -> emptyList();
			case 1 -> singletonList( arrayList.get( 0 ) );
			default -> {
				arrayList.trimToSize();
				yield arrayList;
			}
		};
	}

	public static <O> List<O> combine(Collection<O> list1, Collection<O> list2) {
		final ArrayList<O> combined = arrayList( list1.size() + list2.size() );
		combined.addAll( list1 );
		combined.addAll( list2 );
		return combined;
	}

	public static <O> List<O> combine(List<O> list1, List<O> list2) {
		final ArrayList<O> combined = arrayList( list1.size() + list2.size() );
		combined.addAll( list1 );
		combined.addAll( list2 );
		return combined;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List combineUntyped(List list1, List list2) {
		final ArrayList combined = arrayList( list1.size() + list2.size() );
		combined.addAll( list1 );
		combined.addAll( list2 );
		return combined;
	}

	public static int size(List<?> values) {
		return values == null ? 0 : values.size();
	}

	public static int size(Collection<?> values) {
		return values == null ? 0 : values.size();
	}

	@SafeVarargs
	public static <E> List<E> mutableJoin(Collection<E> first, Collection<E>... others) {
		// it can be empty, but not null
		assert first != null;

		if ( isEmpty( others ) ) {
			final ArrayList<E> list = new ArrayList<>( first.size() );
			list.addAll( first );
			return list;
		}

		final List<E> joined = arrayList( first.size() + ( isEmpty( others ) ? 0 : others.length * 8 ) );
		joined.addAll( first );
		for ( Collection<E> other : others ) {
			joined.addAll( other );
		}
		return joined;
	}
}
