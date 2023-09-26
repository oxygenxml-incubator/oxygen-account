package com.oxygenxml.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.exception.EmailNotConfirmedException;
import com.oxygenxml.account.messages.Message;

/**
 * Service implementation for loading user-specific data for authentication purposes.
 */
@Service
public class OxygenUserDetailsService implements UserDetailsService {

	/**
	 * Service for fetching user details from the database.
	 */
	@Autowired
	private UserService userService;

	/**
	 * Loads a user by their email, primarily for authentication checks.
	 * 
	 * @param email the email of the user to load
     * @return the user details associated with the provided email
     * @throws UsernameNotFoundException if a user with the provided email is not found
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.oxygenxml.account.model.User appUser = userService.getUserByEmail(email);
		
		if(appUser == null) {
			throw new UsernameNotFoundException(Message.USER_NOT_FOUND.getMessage());
		}
		
		if("new".equals(appUser.getStatus())) {
			throw new EmailNotConfirmedException(Message.EMAIL_NOT_CONFIRMED.getMessage());
		}

		UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(appUser.getEmail())
				.password(appUser.getPassword());
		
		return builder.build();
	}
}
