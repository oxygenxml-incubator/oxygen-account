package com.oxygenxml.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oxygenxml.account.model.User;
import com.oxygenxml.account.service.UserService;
import com.oxygenxml.account.service.ValidationService;


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
     *
     * @param userService the service that this controller will use to process user related actions
     */
	@Autowired
	private UserService userService;
	
	/**
     * UserConverter instance responsible for converting between UserDto objects and User entities.
     */
	@Autowired
	private UserConverter userConverter;
	
	/**
     * ValidationService instance responsible for validating the incoming UserDto objects.
     */
	@Autowired
	private ValidationService validationService;
	

	/**
     * Handles the POST request to register a new user.
     * 
     * @param newUser the user to be registered.
     * @return The data transfer object of the newly registered user if successful.
     */
	@PostMapping("/register")
	public UserDto registerUser(@RequestBody UserDto newUserDto){
	
			validationService.validate(newUserDto);
			User newUser = userConverter.dtoToEntity(newUserDto);		
			User registeredUser = userService.registerUser(newUser);
			UserDto registeredUserDto = userConverter.entityToDto(registeredUser);
			return registeredUserDto;
			
		
	}



}

