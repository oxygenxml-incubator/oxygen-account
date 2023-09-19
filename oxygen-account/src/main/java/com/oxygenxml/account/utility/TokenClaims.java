package com.oxygenxml.account.utility;

import lombok.Getter;

@Getter
public enum TokenClaims {


	/**
	 * Status used to indicate that the user is deleted
	 */
	USER_ID("userId"),

	/**
	 * Status used to indicate that the user is active
	 */
	CREATION_DATE("creationDate");

	/**
	 * The string representation of the user status. 
	 */
	private final String tokenClaims;

	/**
	 * Constructs a new {@code UserStatus} enumeration instance with the specified string representation.
	 * @param status The string representation of the user status
	 */
	private TokenClaims(String tokenClaims) {
		this.tokenClaims = tokenClaims;
	}
}
