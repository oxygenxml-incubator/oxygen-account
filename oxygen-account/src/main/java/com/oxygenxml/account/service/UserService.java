package com.oxygenxml.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.oxygenxml.account.messages.Messages;
import com.oxygenxml.account.model.User;
import com.oxygenxml.account.repository.UserRepository;
import com.oxygenxml.account.dto.UserDto;

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
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.EMAIL_ALREADY_EXISTS);
		}
		
		
		System.out.println(newUser);
		
		try {
			return userRepository.save(newUser);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Messages.REGISTRATION_FAILED );
		}
	}
}
