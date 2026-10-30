package org.hibernate.tool.reveng.hbm2x.hbm2hbmxml.IdBagTest;

public class User {
	private String name;

	User() {}

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	void setName(String name) {
		this.name = name;
	}

}
