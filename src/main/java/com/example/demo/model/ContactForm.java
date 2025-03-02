package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ContactForm {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    
    @Column(name = "full_name", nullable = false) // Explicit column mapping
    private String fullName;
    
    @Column(nullable = false) // Add non-null constraint
    private String email;
    
    @Column(columnDefinition = "TEXT", nullable = false) // Restore TEXT type
    private String message;
}
