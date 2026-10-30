package org.hibernate.internal.util;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * A slightly less-broken version of {@link java.util.Optional}.
 *
 * @author Gavin King
 */
sealed public interface Optional<T> extends Serializable {
	record Defined<T>(T result) implements Optional<T> {}
	record Undefined<T>() implements Optional<T> {}

	static <T> Undefined<T> undefined() {
		return new Undefined<>();
	}
	static <T> Defined<T> of(T t) {
		return new Defined<>(t);
	}

	default T evaluate(T value) {
		return this instanceof Defined<T> defined
				? defined.result()
				: value;
	}

	default T evaluate(Supplier<T> supplier) {
		return this instanceof Defined<T> defined
				? defined.result()
				: supplier.get();
	}
}
