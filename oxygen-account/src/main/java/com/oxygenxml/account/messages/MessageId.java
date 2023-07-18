package com.oxygenxml.account.messages;

public enum MessageId {

	 /**
	  * ID corresponding to the message indicating that the username field is empty.
	  */
	 USERNAME_EMPTY("ERR_EMPTY_USERNAME"),

	 /**
	  * ID corresponding to the message indicating that the email field is empty.
	  */
	 EMAIL_EMPTY("ERR_EMPTY_EMAIL"),

	 /**
	  * ID corresponding to the message indicating that the provided email is invalid.
	  */
	 EMAIL_INVALID("ERR_INVALID_EMAIL"),

	 /**
	  * ID corresponding to the message indicating that the password field is empty.
	  */
	 PASSWORD_EMPTY("ERR_EMPTY_PASSWORD"),

	 /**
	  * ID corresponding to the message indicating that the provided password is invalid.
	  */
	 PASSWORD_INVALID("ERR_INVALID_PASSWORD"),
	 /**
	  * ID corresponding to the message indicating that there is already a user with the same email address in the system.
	  */
	 EMAIL_ALREADY_EXISTS("ERR_EMAIL_EXISTS"),
	 /**
	  * ID corresponding to the message indicating that there is already a user with the same username in the system.
	  */
	 USERNAME_ALREADY_EXISTS("ERR_USERNAME_EXISTS"),
	 /**
	  * ID corresponding to the message indicating that the registration process has failed.
	  */
	 REGISTRATION_FAILED("ERR_REGISTRATION_FAILED");

	 private final String id;

	    MessageId(String id) {
	        this.id = id;
	    }

	    public String getId() {
	        return id;
	    }


}
