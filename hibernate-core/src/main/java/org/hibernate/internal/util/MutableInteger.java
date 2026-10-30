package org.hibernate.internal.util;

/**
 * A more performant version of {@link java.util.concurrent.atomic.AtomicInteger} in cases
 * where we do not have to worry about concurrency.  So usually as a variable referenced in
 * anonymous-inner or lambda or ...
 *
 * @author Andrea Boriero
 * @author Steve Ebersole
 */
public class MutableInteger {
	private int value;

	public MutableInteger() {
		this( 0 );
	}

	public MutableInteger(int value) {
		this.value = value;
	}

	public int getAndIncrement() {
		return value++;
	}

	public int get() {
		return value;
	}

	public void set(int value) {
		this.value = value;
	}

	public void increment() {
		++value;
	}

	public void plus(int i) {
		value += i;
	}
}
