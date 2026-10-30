package org.hibernate.envers.internal.entities;

import org.hibernate.envers.configuration.internal.metadata.reader.ComponentAuditingData;
import org.hibernate.envers.internal.entities.mapper.relation.MiddleIdData;

/**
 * @author Felix Feisst (feisst dot felix at gmail dot com)
 */
public class ComponentDescription {

	private final ComponentType type;
	private final String propertyName;
	private final String auditMiddleEntityName;
	private final MiddleIdData middleIdData;

	private ComponentDescription(ComponentType type,
			String propertyName,
			String auditMiddleEntityName,
			MiddleIdData middleIdData) {
		this.type = type;
		this.propertyName = propertyName;
		this.auditMiddleEntityName = auditMiddleEntityName;
		this.middleIdData = middleIdData;
	}

	public static ComponentDescription many(String propertyName, String auditMiddleEntityName, MiddleIdData middleIdData) {
		return new ComponentDescription( ComponentType.MANY, propertyName, auditMiddleEntityName, middleIdData);
	}

	public static ComponentDescription one(String propertyName, ComponentAuditingData componentAuditingData) {
		return new ComponentDescription( ComponentType.ONE, propertyName, null, null);
	}

	public ComponentType getType() {
		return type;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getAuditMiddleEntityName() {
		return auditMiddleEntityName;
	}

	public MiddleIdData getMiddleIdData() {
		return middleIdData;
	}

	public enum ComponentType {
		ONE, MANY
	}
}
