package org.hibernate.orm.test.type.contributor.usertype.hhh18787;

/**
 * Simple object holding some properties
 */
public class CustomData {
	private String text;
	private Long number;

	public CustomData(String text, Long number) {
		this.text = text;
		this.number = number;
	}

	public String getText() {
		return text;
	}

	public Long getNumber() {
		return number;
	}
}
