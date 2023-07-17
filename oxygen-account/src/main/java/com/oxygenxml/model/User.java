package com.oxygenxml.model;


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
import com.oxygenxml.messages.UserMessages;



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
	@NotEmpty(message = UserMessages.ERROR_USERNAME_EMPTY)
	private String username;

	/**
	 *  The email address of the user.
	 */
	@NotEmpty(message = UserMessages.ERROR_EMAIL_EMPTY)
	@Email(message = UserMessages.ERROR_EMAIL_INVALID)
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
	@NotEmpty(message = UserMessages.ERROR_PASSWORD_EMPTY)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = UserMessages.ERROR_PASSWORD_INVALID)
	private String password;

	

}
