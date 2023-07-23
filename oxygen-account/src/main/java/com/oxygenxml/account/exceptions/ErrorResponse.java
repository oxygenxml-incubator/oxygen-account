package com.oxygenxml.account.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The ErrorResponse class represents a standardized structure for error messages
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	
	/**
	 * It represents the internal error code
	 */
	private int internalErrorCode;
	/**
	 * It represents the specific error message
	 */
	private String errorMessage;
	/**
	 * It represents the ID of the error message
	 */
	private String messageId;

}
