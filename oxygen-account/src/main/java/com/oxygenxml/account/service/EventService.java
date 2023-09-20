package com.oxygenxml.account.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.events.RegistrationEvent;
import com.oxygenxml.account.model.User;

import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventService {
    private final EmailService emailService;
    private final JwtService jwtService;

    @EventListener
    public void handleUserRegistration(RegistrationEvent event) {
    	User newUser = event.getUser();
    	
        String token = jwtService.generateEmailConfirmationToken(newUser.getId(), newUser.getRegistrationDate());

        try {
        	emailService.sendEmail(newUser, token);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }
}
