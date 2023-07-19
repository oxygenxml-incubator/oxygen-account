package com.oxygenxml.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.oxygenxml.account.model.User;
import com.oxygenxml.account.service.UserService;

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
     * Handles the POST request to register a new user.
     * 
     * @param newUser the user to be registered.
     * 
     * @return a ResponseEntity with the newly registered user if successful, or the reason for the failure if unsuccessful.
     */
	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@RequestBody User newUser){
		try {
			User user = userService.registerUser(newUser);
			return new ResponseEntity<Object>(user, HttpStatus.CREATED);
			
		}catch(ResponseStatusException e) {
			return new ResponseEntity<Object>(e.getReason(), e.getStatusCode());
		}catch( Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

