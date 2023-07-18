package com.oxygenxml.account.messages;

/**
 * The UserMessages class provides a set of predefined messages that can be used throughout the application 
 * for user interactions.
 */

public class Messages {
	
	/**
	 * Message indicating that the username field is empty.
	 */
	 public static final String EMPTY_USERNAME = "Please insert your username.";
	/**
	 * Message indicating that the email field is empty.
	 */
	 public static final String EMPTY_EMAIL = "Please insert your email.";
	/**
	 * Message indicating that the provided email is invalid.
	 */
	 public static final String INVALID_EMAIL = "Email should be valid.";
	/**
	 * Message indicating that the password field is empty.
	 */
	 public static final String EMPTY_PASSWORD = "Please insert your password.";
	/**
	 * Message indicating that the provided password is invalid.
	 */
	 public static final String INVALID_PASSWORD = "Password must have at least 8 characters, one uppercase letter, one lowercase letter, one number and no spaces.";
    /**
	 * Message indicating that there is already a user with the same email address in the system.
	 */
	 public static final String EMAIL_ALREADY_EXISTS = "User with this email already exists.";
	/**
     * Message indicating that there is already a user with the same username in the system.
	 */
	 public static final String USERNAME_ALREADY_EXISTS = "User with this username already exists.";
    /**
	 * Message indicating that the registration process has failed.
	 */
	 public static final String REGISTRATION_FAILED = "Registration failed. Please try again later.";
	

}
