package org.hibernate.action.queue.internal.constraint;

/// @author Steve Ebersole
public interface Constraint {
	Deferrability getDeferrability();
	boolean isDeferrable();
}
