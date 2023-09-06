package com.oxygenxml.account.dto;

import java.sql.Timestamp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This class is used for transferring data between the client and the application during HTPP requests
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	/**
	 * The name of the user
	 */
	@NotBlank
	private String name;
	
	/**
	 * The email address of the user
	 */
	@NotBlank
	@Email
	private String email;
	
	/**
	 * The password of the user
	 */
	@Size(min = 8)
	private String password;
	
	private String status;
	
	private Timestamp deletionDate;
}
