package org.hibernate.internal.util;

/**
 * @author Christian Beikov
 */
public final class CharSequenceHelper {

	private CharSequenceHelper() {
	}

	public static CharSequence subSequence(CharSequence sequence, int start, int end) {
		if ( start == 0 && end == sequence.length() ) {
			return sequence;
		}
		else if ( sequence instanceof SubSequence ) {
			return sequence.subSequence( start, end );
		}
		else {
			return new SubSequence( sequence, start, end );
		}
	}

	public static boolean isEmpty(CharSequence string) {
		return string == null || string.length() == 0;
	}

	public static boolean regionMatchesIgnoreCase(
			CharSequence lhs,
			int lhsStart,
			CharSequence rhs,
			int rhsStart,
			int length) {
		if ( lhsStart + length <= lhs.length() && rhsStart + length <= rhs.length() ) {
			for ( int i = 0; i < length; i++ ) {
				final char c1 = lhs.charAt( lhsStart + i );
				final char c2 = rhs.charAt( rhsStart + i );
				if ( c1 != c2 && Character.toLowerCase( c1 ) != Character.toLowerCase( c2 ) ) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
