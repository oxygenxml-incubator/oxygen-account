package com.oxygenxml.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**

The User class represents a user entity in the system.

It is annotated with the necessary annotations for persistence.

The class includes basic user information such as username, email, registration date, and password.
*/

@Data
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue
	/**
	 * The ID of the user.
	 */
	private int id;

	/**
	 * The username of the user.
	 */
	private String username;

	/**
	 * The email address of the user.
	 */
	private String email;

	/**
	 * The registration date of the user.
	 */
	private String registrationDate;

	/**
	 * The password of the user.
	 */
	private String password;

	

}
