package com.oxygenxml.account.converter;

import org.springframework.stereotype.Component;

import com.oxygenxml.account.dto.UserDto;
import com.oxygenxml.account.model.User;


@Component
public class UserConverter {
	
	public UserDto entityToDto(User user) {
		
		UserDto userDto = new UserDto();
		
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		
		return userDto;
	}
	
	
	public User dtoToEntity(UserDto userDto) {
	
		User user = new User();
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		
		return user;
	}
	

	

}
