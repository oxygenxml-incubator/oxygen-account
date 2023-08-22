package com.oxygenxml.account.exception;

import java.util.List;

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
	
	/**
	 * It represents a list of validation errors
	 */
	private List<InputValidationError> errors;
	 
	

}
