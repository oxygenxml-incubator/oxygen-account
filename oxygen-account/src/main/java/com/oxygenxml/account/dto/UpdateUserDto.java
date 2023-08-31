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
public class UpdateUserDto {
	
	/**
	 * The new name for the user
	 */
	@NotBlank
	private String name;
}
