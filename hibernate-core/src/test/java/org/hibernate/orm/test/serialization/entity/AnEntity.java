package org.hibernate.orm.test.serialization.entity;

/**
 * The class should be in a package that is different from the test
 * so that the test does not have access to private field,
 * and the protected getter and setter.
 *
 * @author Gail Badner
 */
public class AnEntity {
	public AnEntity() {
	}
}
