package org.hibernate.orm.test.jpa.pack.war;
import jakarta.persistence.PrePersist;

/**
 * @author Emmanuel Bernard
 */
public class IncrementListener {

	@PrePersist
	public void increment(Object entity) {
	}
}
