package com.oxygenxml.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import com.oxygenxml.account.messages.Messages;

@Data
public class UserDto {

	/**
	 * The name of the user
	 */
	@NotEmpty(message = Messages.EMPTY_NAME)
	private String name;
	
	/**
	 * The email address of the user
	 */
	@NotEmpty(message = Messages.EMPTY_EMAIL)
	@Email(message = Messages.INVALID_EMAIL)
	private String email;
	
	/**
	 * The password of the user
	 */
	@NotEmpty(message = Messages.EMPTY_PASSWORD)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = Messages.INVALID_PASSWORD)
	private String password;
}
