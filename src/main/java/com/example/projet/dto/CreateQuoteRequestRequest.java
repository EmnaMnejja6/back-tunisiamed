package com.example.projet.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateQuoteRequestRequest {
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String country;
    private LocalDate dateofBirth;
    private String description;
    private Long specialtyId;
}
