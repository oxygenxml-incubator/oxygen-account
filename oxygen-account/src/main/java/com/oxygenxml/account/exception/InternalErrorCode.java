package com.oxygenxml.account.exception;

import lombok.Getter;

/**
 * This class is used to define specific error codes
 */
@Getter
public enum InternalErrorCode {
	
	/**
	 * It represents the specific error that occurs when a user tries to register with an email that already exists in the system.
	 */
	EMAIL_ALREADY_EXISTS(1);
	/**
	 * It represents the internal error code
	 */
	private final int internalErrorCode;
	/**
	 * It initializes the internalErrorCode with a specific value
	 * @param internalErrorCode  the internal error code that is associated with a specific error
	 */
	InternalErrorCode(int internalErrorCode){
		this.internalErrorCode = internalErrorCode;
	}

}
