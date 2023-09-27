package com.oxygenxml.account.model;

import lombok.Getter;

/**
 * The UserStatus enumeration contains types of status that a user can have.
 */
@Getter
public enum UserStatus {
	
	/**
	 * Status used to indicate that the user is deleted
	 */
	DELETED("deleted"),
	
	/**
	 * Status used to indicate that the user is active
	 */
	ACTIVE("active"),
	
	/**
	 * Status used to indicate that the user is new
	 */
	NEW("new");
	
	/**
	 * The string representation of the user status. 
	 */
	private final String status;

	/**
	 * Constructs a new {@code UserStatus} enumeration instance with the specified string representation.
	 * @param status The string representation of the user status
	 */
	private UserStatus(String status) {
		this.status = status;
	}
}
