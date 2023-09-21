package com.oxygenxml.account.messages;

import lombok.Getter;

/**
 * Defines constants for JWT token claims used in the application.
 */
@Getter
public enum TokenClaims {


	/**
	 * The claim representing the user ID.
	 */
	USER_ID("userId"),

	/**
	 * The claim representing the account creation date.
	 */
	CREATION_DATE("creationDate");

	/**
	 * The string key used to store the claim in the JWT.
	 */
	private final String tokenClaims;

	/**
	 * Constructs a new {@code TokenClaims} enum instance with the specified string key.
	 * 
	 * @param tokenClaims The string key representing the claim in the JWT.
	 */
	private TokenClaims(String tokenClaims) {
		this.tokenClaims = tokenClaims;
	}
}
