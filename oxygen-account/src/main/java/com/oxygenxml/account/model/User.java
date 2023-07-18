package com.oxygenxml.account.model;


import com.oxygenxml.account.messages.Messages;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**

	The User class represents a user entity in the system.

	It is annotated with the necessary annotations for persistence.

	The class includes basic user information such as username, email, registration date, and password.
*/

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	/**
	 * Unique id of the user.
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * The username of the user.
	 */
	@NotEmpty(message = Messages.EMPTY_USERNAME)
	private String username;

	/**
	 *  The email address of the user.
	 */
	@NotEmpty(message = Messages.EMPTY_USERNAME)
	@Email(message = Messages.INVALID_EMAIL)
	private String email;

	/**
	 *  The registration date of the user.
	 *  This field represents the date when the user's account is created.
	 *  It will be automatically saved in the database upon the creation of a new account.
	*/	
	private String registrationDate;

	/**
	 *  The password of the user.
	 */
	@NotEmpty(message = Messages.EMPTY_PASSWORD)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = Messages.INVALID_PASSWORD)
	private String password;

	

}
