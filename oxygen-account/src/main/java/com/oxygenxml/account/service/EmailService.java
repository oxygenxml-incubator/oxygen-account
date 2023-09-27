package com.oxygenxml.account.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.oxygenxml.account.dto.EmailInfo;
import com.oxygenxml.account.exception.EmailException;
import com.oxygenxml.account.messages.EmailMessage;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

/**
 * Service class responsible for sending email messages.
 */
@Service
@AllArgsConstructor
public class EmailService {
	
	/**
     * The JavaMailSender instance used to send email messages.
     */
    final JavaMailSender javaMailSender;
    
    /**
     * The FreeMarker configuration for processing email templates.
     */
    private final Configuration freemarkerConfig;

    /**
     * Sends an email based on the provided EmailInfo.
     *
     * @param emailInfo The EmailInfo object containing email type, address, and email data for template.
     * @throws EmailException If there is an issue with sending the email.
     */
    public void sendEmail(EmailInfo emailInfo) throws EmailException {
    	
    	/**
    	 * Template for the email body content.
    	 */
        Template templateBody;
        
        /**
         * Template for the email subject.
         */
        Template templateSubject;
        
        switch (emailInfo.getType()) {
        case CONFIRM_REGISTRATION:
            try {
                templateBody = freemarkerConfig.getTemplate("registration-body.ftlh");
                templateSubject = freemarkerConfig.getTemplate("registration-subject.ftlh");
            } catch (java.io.IOException e) {
            	throw new EmailException(EmailMessage.EMAIL_TEMPLATE_NOT_FOUND.getMessage(), e);
            }
            break;
        default:
        	throw new EmailException(EmailMessage.EMAIL_TYPE_NOT_FOUND.getMessage(), null);
        }
        
		try {
			String templateBodyContent = FreeMarkerTemplateUtils.processTemplateIntoString(templateBody, emailInfo.getEmailData());
			String templateSubjectContent = FreeMarkerTemplateUtils.processTemplateIntoString(templateSubject, null);
			
			/**
			 * MimeMessage for composing the email.
			 */
	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        
	        /**
	         * Helper class for setting address, subject, and content.
	         */
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
	        
			helper.setTo(emailInfo.getEmailAddress());
			helper.setSubject(templateSubjectContent);
		    helper.setText(templateBodyContent, true);

	        javaMailSender.send(mimeMessage);
		} catch (java.io.IOException | TemplateException e) {
			throw new EmailException(EmailMessage.EMAIL_TEMPLATE_PROCESSING_FAILED.getMessage(), e);
		} catch (MessagingException e) {
			throw new EmailException(EmailMessage.EMAIL_PROPERTIES_NOT_FOUND.getMessage(), e);
		}
    }
}