package com.oxygenxml.account.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.dto.EmailDto;
import com.oxygenxml.account.events.RegistrationEvent;
import com.oxygenxml.account.model.User;
import com.oxygenxml.account.utility.EmailTypes;

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
        
        
        EmailDto emailDto = new EmailDto();
        emailDto.setType(EmailTypes.CONFIRM_REGISTRATION.getEmailType());
        emailDto.setEmailAddress(newUser.getEmail());
        
        Map<String, Object> templateData = new HashMap<>();
		templateData.put("token", token);

		emailDto.setEmailData(templateData);

		
        try {
        	emailService.sendEmail(emailDto);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }
}
