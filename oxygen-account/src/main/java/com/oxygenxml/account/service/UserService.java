package com.oxygenxml.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.exception.InternalErrorCode;
import com.oxygenxml.account.exception.OxygenAccountException;
import com.oxygenxml.account.messages.Message;
import com.oxygenxml.account.model.User;
import com.oxygenxml.account.repository.UserRepository;

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
	 * Instance of BCryptPasswordEncoder used for encoding the password
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	/**
	 * Register a new user in the system.
	 * 
	 * @param newUser The new User entity to be registered.
	 * @return The registered User entity
	 * @throws OxygenAccountException If a user with the same email already exists.
	 */
	public com.oxygenxml.account.model.User registerUser(User newUser) {
				
		if(userRepository.existsByEmail(newUser.getEmail())) {
            throw new OxygenAccountException(Message.EMAIL_ALREADY_EXISTS, HttpStatus.CONFLICT, InternalErrorCode.EMAIL_ALREADY_EXISTS);
        }
		
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		
		return userRepository.save(newUser);
		
	}
	
	/**
	 *  Retrieves a User entity based on the provided email from the database.
	 *  
	 * @param email The email of the User entity to retrieve.
	 * @return A User entity that matches the provided email, or null if no matching User entity is found.
	 */
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User updateUser(User user) {
		return userRepository.save(user);
	}
	
	public User updateCurrentUserName(String email, String newName) {
		User existingUser = getUserByEmail(email);
		existingUser.setName(newName);
		
		return updateUser(existingUser);
	}
}
