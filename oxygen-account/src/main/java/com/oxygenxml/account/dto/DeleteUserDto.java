package com.oxygenxml.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing the data necessary for deleting a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserDto {
	
	/**
	 * The password of the user requesting the deletion.
	 */
	private String password;
}
