package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendConfirmationEmail(String toEmail, String fullName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Thank You for Reaching Out");

            // Fix: Ensure name is provided, otherwise use a default value
            String name = Objects.requireNonNullElse(fullName, "Valued User");

            String emailText = String.format(
                "Dear %s,\n\n" +
                "Thank you for reaching out! I have successfully received your details and message. " +
                "I will review your request and get back to you as soon as possible.\n\n" +
                "If you have any further queries, feel free to reply to this email.\n\n" +
                "Best regards,\n" +
                "Ashmit Zanzote\n" +
                "The Dev Monk | Java Full-Stack Developer",
                name
            );

            message.setText(emailText);

            logger.info("Attempting to send email to: {}", toEmail);
            mailSender.send(message);
            logger.info("Email sent successfully to: {}", toEmail);
        } catch (MailException e) {
            logger.error("FAILED to send email to {}: {}", toEmail, e.getMessage(), e);
            throw e; // Propagate the exception
        }
    }
}
