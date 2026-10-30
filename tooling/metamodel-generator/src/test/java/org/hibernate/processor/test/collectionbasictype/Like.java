package org.hibernate.processor.test.collectionbasictype;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

/**
 * @author Thomas Heigl
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Like<T> {

	@Id
	private Long id;

	public abstract Reference<T> getObject();

	public static class Reference<T> {
	}

}
