package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.ContactForm;
import com.example.demo.repository.ContactFormRepository;

@Service
public class ContactFormServiceImpl implements ContactFormService {  // Implements the interface

    @Autowired
    private ContactFormRepository contactFormRepository;

    @Override
    public ContactForm saveContactForm(ContactForm contactForm) {
        return contactFormRepository.save(contactForm);
    }
}
