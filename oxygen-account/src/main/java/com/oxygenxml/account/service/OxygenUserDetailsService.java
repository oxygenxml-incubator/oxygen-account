package com.oxygenxml.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.messages.Messages;

@Service
public class OxygenUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		com.oxygenxml.account.model.User appUser = userService.getUserByEmail(email);
		if(appUser == null) {
			throw new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessage());
		}

		UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(appUser.getEmail())
				.password(appUser.getPassword());
		return builder.build();
	}
}
