package com.oxygenxml.account.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC Configuration class for registering specific view controllers.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	/**
	 * Overrides the default method to add view controllers to the registry.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {	
		registry.addViewController("/").setViewName("profile");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/profile").setViewName("profile");
	}
}