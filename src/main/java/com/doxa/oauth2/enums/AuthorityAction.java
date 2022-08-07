package com.doxa.oauth2.enums;

/**
 * @author vuducnoi
 */

public enum AuthorityAction {
	/**
	 * View only
	 */
	READ("read"),
	/**
	 * User able to read, create, update, delete
	 */
	WRITE("write"),
	/**
	 * User will be able to approve
	 */
	APPROVE("approve");

	private final String alias;

	AuthorityAction(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return this.alias;
	}
	@Override
	public String toString() {
		return this.alias;
	}
}
