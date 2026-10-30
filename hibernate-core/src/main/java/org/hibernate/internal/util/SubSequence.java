package org.hibernate.internal.util;

/**
 * @author Christian Beikov
 */
public final class SubSequence implements CharSequence {
	private final CharSequence sequence;
	private final int start;
	private final int length;

	public SubSequence(CharSequence sequence, int start, int end) {
		this.sequence = sequence;
		this.start = start;
		this.length = end - start;
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public char charAt(int index) {
		if ( index < 0 || index >= length ) {
			throw new StringIndexOutOfBoundsException( index );
		}
		return sequence.charAt( index + start );
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		if ( start < 0 || start > length ) {
			throw new StringIndexOutOfBoundsException( start );
		}
		if ( end < start || end > length ) {
			throw new StringIndexOutOfBoundsException( end );
		}
		return sequence.subSequence( this.start + start, this.start + end );
	}

	@Override
	public String toString() {
		return sequence.subSequence( start, start + length ).toString();
	}
}
