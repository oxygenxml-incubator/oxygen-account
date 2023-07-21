package com.oxygenxml.account.converter;

import org.springframework.stereotype.Component;

import com.oxygenxml.account.dto.UserDto;
import com.oxygenxml.account.model.User;

/**
 * A utility class that provides methods for converting between UserDto and User
 *
 */

@Component
public class UserConverter {
	
	/**
	 * Converts a User object to a UserDto object.
	 * 
	 * @param user the User object to be converted.
	 * @return the corresponding UserDto object with the name and email
	 */
	
	public UserDto entityToDto(User user) {
		
		UserDto userDto = new UserDto();
		
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		
		return userDto;
	}
	
	/**
	 * Converts a UserDto object to a User object.
	 * 
	 * @param userDto the UserDto object to be converted
	 * @return the corresponding User object with the name, email and password
	 */
	
	public User dtoToEntity(UserDto userDto) {
	
		User user = new User();
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		
		return user;
	}
	

	

}
