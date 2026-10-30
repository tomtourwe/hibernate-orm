package org.hibernate.internal.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Utility class for nullness assertions.
 */
@SuppressWarnings({
		"NullAway", // Nullness utilities are trusted regarding nullness.
		"cast" // Casts look redundant if static nullness analysis is not run.
})
public final class NullnessUtil {

	private NullnessUtil() {
		throw new AssertionError( "shouldn't be instantiated" );
	}

	/**
	 * Marks a method as an initializer,
	 * allowing static analysis to infer correctness of initialization of non-null fields.
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.CLASS)
	public @interface Initializer {
	}

	/**
	 * A method that suppresses warnings from static nullness analysis.
	 *
	 * <p>The method takes a possibly-null reference, unsafely casts it to have the @Nonnull type
	 * qualifier, and returns it. Static nullness analysis considers both the return value, and also the
	 * argument, to be non-null after the method call. Therefore, the {@code castNonNull} method can
	 * be used either as a cast expression or as a statement. Static nullness analysis issues no warnings
	 * in any of the following code:
	 *
	 * <pre><code>
	 *   // one way to use as a cast:
	 *  {@literal @}Nonnull String s = castNonNull(possiblyNull1);
	 *
	 *   // another way to use as a cast:
	 *   castNonNull(possiblyNull2).toString();
	 *
	 *   // one way to use as a statement:
	 *   castNonNull(possiblyNull3);
	 *   possiblyNull3.toString();`
	 * </code></pre>
	 * <p>
	 * The {@code castNonNull} method is intended to be used in situations where the programmer
	 * definitively knows that a given reference is not null, but the type system is unable to make
	 * this deduction. It is not intended for defensive programming, in which a programmer cannot
	 * prove that the value is not null but wishes to have an earlier indication if it is.
	 *
	 * <p>The method throws {@link AssertionError} if Java assertions are enabled and the argument is
	 * {@code null}. If the exception is ever thrown, then that indicates that the programmer misused
	 * the method by using it in a circumstance where its argument can be null.
	 *
	 * @param <T> the type of the reference
	 * @param ref a reference of @Nullable type, that is non-null at run time
	 *
	 * @return the argument, casted to have the type qualifier @Nonnull
	 */
	public static <T extends Object> @Nonnull T castNonNull(@Nullable T ref) {
		assert ref != null : "Misuse of castNonNull: called with a null argument";
		return ref;
	}
}
