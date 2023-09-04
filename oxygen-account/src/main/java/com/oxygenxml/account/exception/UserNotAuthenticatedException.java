package com.oxygenxml.account.exception;

import com.oxygenxml.account.messages.Message;

/**
 * This exception is thrown when a user is not authenticated in the system.
 *
 */
public class UserNotAuthenticatedException extends RuntimeException {
	
	/**
	 * Unique ID used in serialization to verify that the sender and receiver of a serialized object maintain compatibility.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new UserNotAuthenticatedException with the provided message.
	 * 
	 * @param message The error message associated with this exception.
	 */
	public UserNotAuthenticatedException(Message message) {
        super(message.getMessage());
    }
}
