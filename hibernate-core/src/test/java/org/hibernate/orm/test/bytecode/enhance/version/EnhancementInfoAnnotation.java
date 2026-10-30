package org.hibernate.orm.test.bytecode.enhance.version;

import org.hibernate.Version;
import org.hibernate.bytecode.enhance.spi.EnhancementInfo;

import java.lang.annotation.Annotation;

/**
 * @author Steve Ebersole
 */
public class EnhancementInfoAnnotation implements EnhancementInfo {
	private String version;
	private boolean includesDirtyChecking;
	private boolean includesAssociationManagement;

	public EnhancementInfoAnnotation() {
		this( Version.getVersionString() );
	}

	public EnhancementInfoAnnotation(String version) {
		this( version, false, false );
	}

	public EnhancementInfoAnnotation(
			String version,
			boolean includesDirtyChecking,
			boolean includesAssociationManagement) {
		this.version = version;
		this.includesDirtyChecking = includesDirtyChecking;
		this.includesAssociationManagement = includesAssociationManagement;
	}

	@Override
	public String version() {
		return version;
	}

	@Override
	public boolean includesDirtyChecking() {
		return includesDirtyChecking;
	}

	@Override
	public boolean includesAssociationManagement() {
		return includesAssociationManagement;
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return EnhancementInfo.class;
	}
}
