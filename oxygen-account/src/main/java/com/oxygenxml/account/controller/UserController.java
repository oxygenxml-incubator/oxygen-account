package com.oxygenxml.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.oxygenxml.account.model.User;
import com.oxygenxml.account.service.UserService;
import com.oxygenxml.account.converter.UserConverter;
import com.oxygenxml.account.dto.UserDto;

/**
 * The UserControllerclass is a REST controller that manages HTTP requests related to users.
 * This controller handles the registration of a new user into the system.
 */

@RestController
@RequestMapping("/api/users")
public class UserController {
	

	/**
     * Constructs a  UserController with a specified UserService
     */
	@Autowired
	private UserService userService;
	
	/**
	 * The UserConverter to convert between UserDto and User entities
	 */
	@Autowired
	private UserConverter userConverter;

	/**
     * Handles the POST request to register a new user.
     * 
     * @param newUserDto the user to be registered.
     * 
     * @return a UserDto with the newly registered user.
     */
	@PostMapping("/register")
	public UserDto registerUser(@RequestBody UserDto newUserDto){
		
			User newUser = userConverter.dtoToEntity(newUserDto);
			User registeredUser = userService.registerUser(newUser);
			UserDto registeredUserDto = userConverter.entityToDto(registeredUser);
			return registeredUserDto;
			
		
	}
}

