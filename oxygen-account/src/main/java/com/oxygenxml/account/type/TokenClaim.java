package com.oxygenxml.account.type;

import lombok.Getter;

/**
 * Defines constants for JWT token claims used in the application.
 */
@Getter
public enum TokenClaim {

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
	private final String name;

	/**
	 * Constructs a new {@code TokenClaims} enum instance with the specified string key.
	 * 
	 * @param name The string key representing the claim in the JWT.
	 */
	private TokenClaim(String name) {
		this.name = name;
	}
}
