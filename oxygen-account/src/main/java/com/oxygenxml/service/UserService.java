package com.oxygenxml.service;

import com.oxygenxml.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.oxygenxml.model.User;
import com.oxygenxml.messages.UserServiceMessages;

/**
 *  Service class for user-related operations.
 */
@Service
public class UserService {
	
	/**
	 * Instance of UserRepository to interact with the database.
	 */
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Register a new user in the system.
	 * 
	 * @param newUser The new User entity to be registered.
	 * @return The registered User entity, or a ResponseStatusException if an error occurs.
	 */
	
	public User registerUser(User newUser) {
		if(userRepository.existsByEmail(newUser.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, UserServiceMessages.EMAIL_ALREADY_EXISTS);
		}
		
		if(userRepository.existsByUsername(newUser.getUsername())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, UserServiceMessages.USERNAME_ALREADY_EXISTS);
		}
		
		try {
			return userRepository.save(newUser);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, UserServiceMessages.REGISTRATION_FAILED );
		}
	}
}
