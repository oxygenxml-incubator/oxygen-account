package com.oxygenxml.account.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.oxygenxml.account.model.User;

import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {
    final JavaMailSender javaMailSender;

    public void sendEmail(User user) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Confirm oxygen account");
        helper.setTo(user.getEmail());
        helper.setText("Te rugam sa confirmi emailul", true);
        javaMailSender.send(mimeMessage);
    }
}