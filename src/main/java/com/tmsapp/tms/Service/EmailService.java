package com.tmsapp.tms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String email_to, String email_subject, String email_body) {
    SimpleMailMessage message = new SimpleMailMessage();

    message.setTo(email_to);
    message.setSubject(email_subject);
    message.setText(email_body);

    javaMailSender.send(message);
}
    
}
