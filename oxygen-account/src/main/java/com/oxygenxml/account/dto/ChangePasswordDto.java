package com.oxygenxml.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
	
	@Size(min = 8)
	private String newPassword;
	
	@NotBlank
	private String oldPassword;

}
