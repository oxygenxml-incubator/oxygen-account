package com.oxygenxml.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
	@NotEmpty
	private String name;
	
	/**
	 * The email address of the user
	 */
	@NotEmpty
	@Email
	private String email;
	
	/**
	 * The password of the user
	 */
	@NotEmpty
	private String password;
}
