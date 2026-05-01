package com.example.projet.dto;

import lombok.Data;

@Data
public class CreateDoctorRequest {
    private String firstName;
    private String lastName;
    private String photoUrl;
    private Integer experienceYears;
    private String diploma;
    private String biography;
    private Long clinicId;
    private Long specialtyId;
}
