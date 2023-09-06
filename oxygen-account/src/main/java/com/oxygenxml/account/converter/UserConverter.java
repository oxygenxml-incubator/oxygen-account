package com.oxygenxml.account.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oxygenxml.account.dto.UserDto;
import com.oxygenxml.account.model.User;
import com.oxygenxml.account.service.UserService;

/**
 * A utility class that provides methods for converting between {@link UserDto} and {@link User}
 */

@Component
public class UserConverter {
	
	@Autowired
	private UserService userService;
	/**
	 * Converts a {@link User} object to a {@link UserDto} object.
	 * 
	 * @param user the User object to be converted.
	 * @return the corresponding UserDto object with the name and email
	 */
	
	public UserDto entityToDto(User user) {
		
		UserDto userDto = new UserDto();
		
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setStatus(user.getStatus());
		
		if ("deleted".equals(user.getStatus())) {
	        int daysLeft = userService.getDaysLeftForRecovery(user);
	        userDto.setDaysLeftForRecovery(daysLeft);
	    } else if ("active".equals(user.getStatus())) {
	    	userDto.setDaysLeftForRecovery(-1);
	    }
		
		return userDto;
	}
	
	/**
	 * Converts a {@link UserDto} object to a {@link User} object.
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
