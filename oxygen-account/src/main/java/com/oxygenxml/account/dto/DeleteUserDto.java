package com.oxygenxml.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO representing the data necessary for deleting a user.
 */
@Getter
@Setter
@NoArgsConstructor
public class DeleteUserDto {
	
	/**
	 * The password of the user requesting the deletion.
	 */
	private String password;
}
