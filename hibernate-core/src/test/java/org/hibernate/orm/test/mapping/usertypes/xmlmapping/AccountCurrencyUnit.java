package org.hibernate.orm.test.mapping.usertypes.xmlmapping;

public class AccountCurrencyUnit implements CurrencyUnit {

	public AccountCurrencyUnit() {
	}

	public AccountCurrencyUnit(String code) {
	}

	@Override
	public String getCurrencyCode() {
		return null;
	}

	@Override
	public int getNumericCode() {
		return 0;
	}

}
