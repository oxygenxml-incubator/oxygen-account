package com.oxygenxml.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oxygenxml.account.model.User;
import com.oxygenxml.account.service.UserService;
import com.oxygenxml.account.service.ValidationService;
import com.oxygenxml.account.exception.OxygenAccountException;


import com.oxygenxml.account.converter.UserConverter;
import com.oxygenxml.account.dto.UserDto;

import java.util.HashMap;
import java.util.Map;
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
	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private ValidationService validationService;
	

	/**
     * Handles the POST request to register a new user.
     * 
     * @param newUser the user to be registered.
     * 
     * @return a ResponseEntity with the newly registered user if successful, or the reason for the failure if unsuccessful.
     */
	@PostMapping("/register")
	public UserDto registerUser(@RequestBody UserDto newUserDto){
	
			User newUser = userConverter.dtoToEntity(newUserDto);		
			validationService.validate(newUserDto);
			User registeredUser = userService.registerUser(newUser);
			UserDto registeredUserDto = userConverter.entityToDto(registeredUser);
			return registeredUserDto;
			
		
	}
	/**
     * Handles OxygenAccountException thrown in the application.
     *
     * @param exception the instance of OxygenAccountException
     * @return ResponseEntity containing error details: the error message, internal error code and HTTP status code, along with the corresponding HTTP status
     */
	@ExceptionHandler(OxygenAccountException.class)
	public ResponseEntity<?> handleOxygenAccountExceptions(OxygenAccountException exception) {
	    Map<String, String> errorResponse = new HashMap<>();
	    errorResponse.put("message", exception.getMessage());
	    errorResponse.put("code", String.valueOf(exception.getErrorCode().getInternalErrorCode()));
	    errorResponse.put("status", String.valueOf(exception.getStatus().value()));

	    return new ResponseEntity<>(errorResponse, exception.getStatus());
	}


}

