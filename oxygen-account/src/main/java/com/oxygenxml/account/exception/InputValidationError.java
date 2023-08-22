package com.oxygenxml.account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The InputValidationError class encapsulates information about a specific validation error related to user input.
 */
@Getter
@AllArgsConstructor
public class InputValidationError {

	/**
	 * The name of the field that caused the violation error.
	 */
	private String fieldName;
	
	/**
	 * The error message that describes the cause of the error
	 */
	private String errorMessage;
	
	/**
	 * The message id that represents the ID associate with the error message.
	 */
	private String messageId;
}
