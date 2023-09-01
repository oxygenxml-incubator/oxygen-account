package com.oxygenxml.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.oxygenxml.account.converter.UserConverter;
import com.oxygenxml.account.dto.ChangePasswordDto;
import com.oxygenxml.account.dto.UpdateUserNameDto;
import com.oxygenxml.account.dto.UserDto;
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
	 * Registers a new user based on the provided DTO and then converts the registered user back to a DTO.
	 *
	 * @param newUserDto The data transfer object containing user details to be registered.
	 * @return UserDto representing the registered user.
	 */
	public UserDto registerAndConverttUser(UserDto newUserDto) {
		validationService.validate(newUserDto);
		User newUser = userConverter.dtoToEntity(newUserDto);		
		User registeredUser = registerUser(newUser);
		return userConverter.entityToDto(registeredUser);
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
	
	/**
	 * Saves or updates the given user entity in the repository.
	 * 
	 * @param user the user entity to be saved or updated.
	 * @return the saved or updated user entity.
	 */
	public User updateUser(User user) {
		return userRepository.save(user);
	}
	
	/**
	 * Retrieves the currently authenticated user from the security context.
	 * 
	 * @return The authenticated User entity.
	 */
	public User getCurrentUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User userPrincipal) {
		        return userRepository.findByEmail(userPrincipal.getUsername());
		}
		
		throw new UsernameNotFoundException(Message.INVALID_USER.getMessage());
	}
	
	/**
	 * Retrieves the DTO representation of the currently authenticated user.
	 * 
	 * @return A UserDto representation of the authenticated user. If the user is not found, a default anonymous UserDto is returned.
	 */
	public UserDto getCurrentUserDto() {
		try {
			User currentUser = getCurrentUser();
	        return userConverter.entityToDto(currentUser);
			
		} catch (UsernameNotFoundException e) {
			return new UserDto("Anonymous User", "anonymousUser", null);
		}
	}
	
	/**
     * Updates the name of the user identified by the given email.
     * 
     * @param email the email address used to identify the user.
     * @param newName the new name to be set for the user.
     * @return the updated user entity.
     */
	public UserDto updateUserName(UpdateUserNameDto nameChange) {
		
		validationService.validate(nameChange);
		
		User currentUser = getCurrentUser();
		currentUser.setName(nameChange.getName());
		userRepository.save(currentUser);
		return userConverter.entityToDto(currentUser);
	}
	
	/**
	 * Updates the password of the currently authenticated user after validating the input data.
	 * 
	 * @param changePasswordDto Data transfer object containing details about the old and new passwords.
	 * @return A UserDto representation of the user after the password has been updated.
	 */
	public UserDto updateCurrentUserPassword(ChangePasswordDto changePasswordDto) {
		User currentUser = getCurrentUser();
		validationService.validate(changePasswordDto);
		
		 if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), currentUser.getPassword())) {
			throw new OxygenAccountException(Message.INCORRECT_PASSWORD, HttpStatus.FORBIDDEN, InternalErrorCode.INCORRECT_PASSWORD);
			
		} else if (changePasswordDto.getOldPassword().equals(changePasswordDto.getNewPassword())) {
			throw new OxygenAccountException(Message.PASSWORD_SAME_AS_OLD, HttpStatus.FORBIDDEN, InternalErrorCode.PASSWORD_SAME_AS_OLD);
		}
		
		 currentUser.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
		 
		 userRepository.save(currentUser);
		
		 return userConverter.entityToDto(currentUser);
	}
}
