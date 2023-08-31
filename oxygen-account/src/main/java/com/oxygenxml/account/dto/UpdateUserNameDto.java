package com.oxygenxml.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Data Transfer Object for updating the user's name.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserNameDto {
	
	/**
	 * The name of the user. This field cannot be blank.
	 */
	@NotBlank
	private String name;
}
