package com.oxygenxml.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.exception.InternalErrorCode;
import com.oxygenxml.account.exception.OxygenAccountException;
import com.oxygenxml.account.messages.Messages;
import com.oxygenxml.account.model.User;
import com.oxygenxml.account.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 *  Service class for user-related operations.
 */

@Service
@AllArgsConstructor
public class UserService {
	
	/**
	 * Instance of UserRepository to interact with the database.
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * Instance of BCryptPasswordEncoder used for encoding the password
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private final ValidationService userServiceValidation;
	
	
	/**
	 * Register a new user in the system.
	 * 
	 * @param newUser The new User entity to be registered.
	 * @return The registered User entity, or a ResponseStatusException if an error occurs.
	 */
	
	public User registerUser(User newUser) {
		
		
		userServiceValidation.validate(newUser);
		
		if(userRepository.existsByEmail(newUser.getEmail())) {
            throw new OxygenAccountException(Messages.EMAIL_ALREADY_EXISTS, HttpStatus.CONFLICT, InternalErrorCode.EMAIL_ALREADY_EXISTS);
        }
		
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		
		return userRepository.save(newUser);
		
	}
	
}
