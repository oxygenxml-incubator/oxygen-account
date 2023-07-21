package com.oxygenxml.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.messages.Messages;
import com.oxygenxml.account.model.User;
import com.oxygenxml.account.repository.UserRepository;
import com.oxygenxml.account.exceptions.InternalErrorCode;
import com.oxygenxml.account.exceptions.OxygenAccountExceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *  Service class for user-related operations.
 */

@Service
@AllArgsConstructor
@NoArgsConstructor
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
			throw new OxygenAccountExceptions(Messages.EMAIL_ALREADY_EXISTS, HttpStatus.CONFLICT, InternalErrorCode.EMAIL_ALREADY_EXISTS);
		}
		
		return userRepository.save(newUser);
		
	}
	
	/**
	 * Method used to delete the databese during tests
	 */
	public void deleteAll() {
		userRepository.deleteAll();
	}
}
