package com.example.demo.controller;

import jakarta.validation.Valid;
import java.util.Map; // Add this import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException; // Add this import
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.ContactForm;
import com.example.demo.service.ContactFormService;
import com.example.demo.service.EmailService;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = {
	    "https://portfolio-frontend-wa1y.onrender.com", // My new Portfolio frontend URL
	    "http://localhost:3000"                    // Keep for local testing
	})
public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitContactForm(@Valid @RequestBody ContactForm contactForm) {
        try {
            ContactForm savedForm = contactFormService.saveContactForm(contactForm);
            
            try {
                emailService.sendConfirmationEmail(contactForm.getEmail(), contactForm.getFullName());
                return ResponseEntity.ok(Map.of(
                    "message", "Form submitted and email sent",
                    "data", savedForm
                ));
            } catch (MailException e) {
                // Return email error separately
                return ResponseEntity.ok(Map.of(
                    "message", "Form saved but email failed",
                    "data", savedForm,
                    "emailError", e.getMessage()
                ));
            }
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Database Error: " + e.getMessage()
            ));
        }
    }
}