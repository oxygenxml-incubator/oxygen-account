package com.oxygenxml.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Data Transfer Object for updating the user's name.
 */
@Getter
@Setter
@NoArgsConstructor
public class UpdateUserNameDto {
	
	/**
	 * The new name for the user
	 */
	@NotBlank
	private String name;
}
