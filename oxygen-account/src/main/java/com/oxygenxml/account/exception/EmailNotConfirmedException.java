package com.oxygenxml.account.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 *This class represents a custom exception which is thrown when a user tries to log in but their email is not confirmed.
 */
public class EmailNotConfirmedException extends InternalAuthenticationServiceException{

	/**
	 * Unique ID used in serialization to verify that the sender and receiver of a serialized object maintain compatibility.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new EmailNotConfirmedException with the specified detail msg
	 */
	public EmailNotConfirmedException(String msg) {
		super(msg);
	}

}
