package com.oxygenxml.messages;

/**
 * The UserServiceMessages class provides a set of predefined messages that can be used throughout the User service.
 */

public class UserServiceMessages {
	
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
