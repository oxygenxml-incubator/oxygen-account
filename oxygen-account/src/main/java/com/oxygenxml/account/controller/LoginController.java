package com.oxygenxml.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for managing the login functionality.
 */
@Controller
public class LoginController {
	
    /**
     * Method for displaying the login form.
     */
    @GetMapping("/login")
    public String showLoginForm() {
    	return "login";
    }
}