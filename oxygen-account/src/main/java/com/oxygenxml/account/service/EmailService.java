package com.oxygenxml.account.service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.oxygenxml.account.dto.EmailDto;
import com.oxygenxml.account.exception.OxygenAccountException;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

import com.oxygenxml.account.utility.EmailTypes;

@Service
@AllArgsConstructor
public class EmailService {
    final JavaMailSender javaMailSender;
    private final Configuration freemarkerConfig;

    public void sendEmail(EmailDto emailDto) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        
        helper.setTo(emailDto.getEmailAddress());       
        
        Template template;
        
        if (EmailTypes.CONFIRM_REGISTRATION.getEmailType().equals(emailDto.getType())) {
        	helper.setSubject("Confirm Registration");
    		try {
				template = freemarkerConfig.getTemplate("registration.ftlh");
			} catch (java.io.IOException e) {
				throw new OxygenAccountException(null, null, null);
			}
        } else {
        	throw new OxygenAccountException(null, null, null);
        }
        
        String templateContent;
		try {
			templateContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, emailDto.getEmailData());
	        helper.setText(templateContent, true);
		} catch (java.io.IOException | TemplateException e) {
			throw new OxygenAccountException(null, null, null);
		}

        javaMailSender.send(mimeMessage);
    }
}