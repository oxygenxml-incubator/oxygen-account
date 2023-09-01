package com.oxygenxml.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oxygenxml.account.dto.ChangePasswordDto;
import com.oxygenxml.account.dto.UpdateUserNameDto;
import com.oxygenxml.account.dto.UserDto;
import com.oxygenxml.account.service.UserService;

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
     * Handles the POST request to register a new user.
     * 
     * @param newUser the user to be registered.
     * @return The data transfer object of the newly registered user if successful.
     */
	@PostMapping("/register")
	public UserDto registerUser(@RequestBody UserDto newUserDto){
		return userService.registerAndConverttUser(newUserDto);
	}
	
	/**
     * Handles the GET request to fetch profile details of the authenticated user.
     * 
     * @return The profile details of the authenticated user.
     */
    @GetMapping("/me")
    public UserDto getCurrentUser() {
    	return userService.getCurrentUserDto();
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
    	return userService.updateUserName(nameChange);
    }

    /**
     * Updates the password of the currently authenticated user based on the provided {@link ChangePasswordDto}
     * 
     * @param changePasswordDto A DTO containing the new password and the old password.
     * @return A DTO representation of the updated user or null if the user is not recognized.
     */
    @PutMapping("/password")
    public UserDto changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
    	return userService.updateCurrentUserPassword(changePasswordDto);
    }
}
	
	

	
