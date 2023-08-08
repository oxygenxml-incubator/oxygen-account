package com.oxygenxml.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller responsible for managing the login functionality.
 */
@Controller
public class LoginController {
	
    /**
     * Method for displaying the login form.
     */
    @RequestMapping(path = "/login",  method = {RequestMethod.GET})
    public String showLoginForm() {
    	return "login";
    }
}