package com.oxygenxml.account.messages;


public enum Messages {

	 /**
	  * ID corresponding to the message indicating that the username field is empty.
	  */
	 USERNAME_EMPTY("EMPTY_NAME", "Please insert your username."),

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
	 PASSWORD_INVALID("INVALID_PASSWORD", "Password must have at least 8 characters, one uppercase letter, one lowercase letter, one number and no spaces."),
	 /**
	  * ID corresponding to the message indicating that there is already a user with the same email address in the system.
	  */
	 EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "User with this email already exists."),
	 /**
	  * ID corresponding to the message indicating that there is already a user with the same username in the system.
	  */
	 USERNAME_ALREADY_EXISTS("USERNAME_ALREADY_EXISTS", "User with this username already exists."),
	 /**
	  * ID corresponding to the message indicating that the registration process has failed.
	  */
	 REGISTRATION_FAILED("REGISTRATION_FAILED", "Registration failed. Please try again later.");

	 private final String id;
	 private final String message;

	    Messages(String id, String message) {
	        this.id = id;
	        this.message = message;
	    }

	    public String getId() {
	        return id;
	    }
	    
	    public String getMessage() {
	    	return message;
	    }


}
