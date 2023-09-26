package com.oxygenxml.account.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

public class EmailNotConfirmedException extends InternalAuthenticationServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailNotConfirmedException(String msg) {
		super(msg);
	}

}
