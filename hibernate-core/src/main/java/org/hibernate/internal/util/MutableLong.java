package org.hibernate.internal.util;

/**
 * A more performant version of {@link java.util.concurrent.atomic.AtomicLong} in cases
 * where we do not have to worry about concurrency.  So usually as a variable referenced in
 * anonymous-inner or lambda or ...
 *
 * @author Andrea Boriero
 */
public class MutableLong {
	private long value;

	public MutableLong() {
	}

	public long get() {
		return value;
	}

	public void set(long value) {
		this.value = value;
	}
}
