package com.oxygenxml.account.type;

import lombok.Getter;

@Getter
public enum UrlAnchor {

	INVALID_USER("/login#invalid-user"),
	
	/**
	 * The claim representing the user ID.
	 */
	SUCCES_CONFIRMATION("/login#success-confirmation"),

	/**
	 * The claim representing the account creation date.
	 */
	INVALID_TOKEN("/login#invalid-token"),
	
	TOKEN_EXPIRED("/login#token-expired"),
	
	USER_ALREADY_CONFIRMED("/login#user-already-confirmed");

	/**
	 * The string key used to store the claim in the JWT.
	 */
	private final String anchor;

	/**
	 * Constructs a new {@code TokenClaims} enum instance with the specified string key.
	 * 
	 * @param name The string key representing the claim in the JWT.
	 */
	private UrlAnchor(String anchor) {
		this.anchor = anchor;
	}
}
