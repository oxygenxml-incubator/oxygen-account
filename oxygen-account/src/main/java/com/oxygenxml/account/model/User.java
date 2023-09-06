package com.oxygenxml.account.model;


import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**

	The User class represents a user entity in the system.

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * The name of the user.
	 */
	private String name;

	/**
	 *  The email address of the user.
	 */
	@Column(name = "email")
	private String email;

	/**
	 *  The registration date of the user.
	 *  This field represents the date when the user's account is created.
	 *  It will be automatically saved in the database upon the creation of a new account.
	*/	
	@Column(name = "registration_date", insertable = false)
	private Timestamp registrationDate;
	

	/**
	 *  The password of the user.
	 */
	private String password;
	
	@Column( name = "status", insertable = false)
	private String status;
	
	@Column(name = "deletion_date", insertable = false)
	private Timestamp deletionDate;
}
