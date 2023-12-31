package com.oxygenxml.account.messages;

/**
 * The Messages enumeration contains various types of error messages and their unique identifiers associated with them.
 */

public enum Message {

	/**
	 * ID corresponding to the message indicating that the username field is empty.
	 */
	NAME_EMPTY("EMPTY_NAME", "The name cannot be empty."),

	/**
	 * ID corresponding to the message indicating that the email field is empty.
	 */
	EMAIL_EMPTY("EMPTY_EMAIL", "Please insert your email."),

	/**
	 * ID corresponding to the message indicating that the provided email is invalid.
	 */
	EMAIL_INVALID("INVALID_EMAIL", "Email should be valid."),

	/**
	 * ID corresponding to the message indicating that the password field is empty.
	 */
	PASSWORD_EMPTY("EMPTY_PASSWORD", "Please insert your password."),

	/**
	 * ID corresponding to the message indicating that the provided password is invalid.
	 */
	PASSWORD_INVALID("INVALID_PASSWORD", "Password must have at least 8 characters."),

	/**
	 * ID corresponding to the message indicating that there is already a user with the same email address in the system.
	 */
	EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "An account with this email address already exists."),

	/**
	 * ID corresponding to the message indicating that the action failed during execution.
	 */
	BAD_REQUEST("BAD_REQUEST", "Bad request."),

	/**
	 * ID corresponding to the message indicating that the field is empty.
	 */
	EMPTY_FIELD("EMPTY_FIELD", "Please provide a non-empty value."),

	/**
	 * ID corresponding to the message indicating that the field is too short.
	 */
	SHORT_FIELD("SHORT_FIELD", "Input field is too short. Please enter a longer value."),

	/**
	 * ID corresponding to the message indicating that the email or password are incorrect
	 */
	USER_NOT_FOUND("USER_NOT_FOUND", "Incorrect email or password."),

	/**
	 * ID corresponding to the message indicating that the validation failed.
	 */
	INPUT_VALIDATION_FAILED("INPUT_VALIDATION_FAILED", "Input validation failed."),
	
	/**
	 * ID corresponding to the message indicating that the password is incorrect.
	 */
	INCORRECT_PASSWORD("INCORRECT_PASSWORD","Incorrect password."),
	
	/**
	 * ID corresponding to the message indicating that the new password is the same as the old one
	 */
	PASSWORD_SAME_AS_OLD("PASSWORD_SAME_AS_OLD", "The new password is the same as the old one."),
	
	/**
	 * ID corresponding to the message indicating that the user is not authenticated
	 */
	USER_NOT_AUTHENTICATED("USER_NOT_AUTHENTICATED", "User not authenticated."),

	/**
	 * ID corresponding to the message indicating that the user was already confirmed
	 */
	USER_ALREADY_CONFIRMED("USER_ALREADY_CONFIRMED", "The user has already been confirmed."),

	/**
	 * ID corresponding to the message indicating that the link has expired
	 */
	TOKEN_EXPIRED("TOKEN_EXPIRED", "The confirmation link has expired"),

	/**
	 * ID corresponding to the message indicating that the link is invalid
	 */
	INVALID_TOKEN("INVALID_TOKEN", "The confirmation link is invalid."),

	/**
	 * ID corresponding to the message indicating that the email is not confirmed
	 */
	EMAIL_NOT_CONFIRMED("EMAIL_NOT_CONFIRMED", "Email not confirmed.");

	/**
	 * Unique identifier corresponding to each type of message.
	 */
	private final String id;

	/**
	 * The error message associated with each unique identifier
	 */
	private final String message;

	/**
	 * Constructs a new instance of Messages with the specified identifier and message
	 * @param id the unique identifier for the message
	 * @param message the error message associated with the identifier
	 */
	private Message(String id, String message) {
		this.id = id;
		this.message = message;
	}

	/**
	 * Return the unique identifier of the message.
	 * @return the identifier
	 */
	public String getId() {
		return id;
	}
	/**
	 * Returns the error message associated with the identifier of the message
	 * @return the error message
	 */
	public String getMessage() {
		return message;
	}


}
