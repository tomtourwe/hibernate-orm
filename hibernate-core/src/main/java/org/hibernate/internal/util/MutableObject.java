package org.hibernate.internal.util;

/**
 * Mutable object reference.  Mainly useful with anonymous code blocks
 * and lambdas.
 *
 * @author Steve Ebersole
 * @author Andrea Boriero
 */
public class MutableObject<T> {
	private T reference;

	public MutableObject() {
	}

	public T get() {
		return reference;
	}

	public void set(T reference) {
		this.reference = reference;
	}
}
