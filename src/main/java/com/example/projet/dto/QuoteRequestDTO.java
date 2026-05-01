package com.example.projet.dto;

import com.example.projet.entities.enums.QuoteStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class QuoteRequestDTO {
    private Long id;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String country;
    private LocalDate dateofBirth;
    private String description;
    private QuoteStatus status;
    private String token;
    private LocalDateTime createdAt;
    // Specialty info
    private SpecialtyDTO specialty;
}
