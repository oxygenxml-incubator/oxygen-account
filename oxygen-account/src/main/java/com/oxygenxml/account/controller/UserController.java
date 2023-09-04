package com.oxygenxml.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oxygenxml.account.converter.UserConverter;
import com.oxygenxml.account.dto.ChangePasswordDto;
import com.oxygenxml.account.dto.UpdateUserNameDto;
import com.oxygenxml.account.dto.UserDto;
import com.oxygenxml.account.exception.UserNotAuthenticatedException;
import com.oxygenxml.account.model.User;
import com.oxygenxml.account.service.UserService;
import com.oxygenxml.account.service.ValidationService;

/**
 * The UserControllerclass is a REST controller that manages HTTP requests related to users.
 * This controller handles the registration of a new user into the system.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
	

	/**
	 * UserService used for user-related operations.
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
		return userConverter.entityToDto(registeredUser);
	}
	
	/**
     * Handles the GET request to fetch profile details of the authenticated user.
     * 
     * @return The profile details of the authenticated user.
     */
	@GetMapping("/me")
	public UserDto getCurrentUser() {
		try {
			User currentUser = userService.getCurrentUser();
			return userConverter.entityToDto(currentUser);

		} catch (UserNotAuthenticatedException e) {
			return new UserDto("Anonymous User", "anonymousUser", null);
		}
	}
    
    
    /**
     * Updates the name of the currently authenticated user based on the provided {@link UpdateUserNameDto}.
     * 
     * @param nameChange  A DTO containing the new name for the user.
     * @return A DTO representation of the updated user or null if the user is not recognized.
     * @throws Exception if validation fails. 
     */
    @PutMapping("/profile")
    public UserDto updateUserName(@RequestBody UpdateUserNameDto nameChange ) {
    	validationService.validate(nameChange);
    	User updatedUser = userService.updateUserName(userConverter.updateUserNameDtoToEntity(nameChange));
        return userConverter.entityToDto(updatedUser);
    }

    /**
     * Updates the password of the currently authenticated user based on the provided {@link ChangePasswordDto}
     * 
     * @param changePasswordDto A DTO containing the new password and the old password.
     * @return A DTO representation of the updated user or null if the user is not recognized.
     */
    @PutMapping("/password")
    public UserDto changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
    	validationService.validate(changePasswordDto);
    	User updatedUser = userService.updateCurrentUserPassword(changePasswordDto);
    	return userConverter.entityToDto(updatedUser);
    }
}
	
	

	
