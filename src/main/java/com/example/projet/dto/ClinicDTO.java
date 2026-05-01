package com.example.projet.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ClinicDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String city;
    private Double latitude;
    private Double longitude;
    private String phone;
    private String email;
    private String imageUrl;
    private Double rating;
    private LocalDateTime createdAt;
    // Admin info (no password)
    private Long clinicAdminId;
    private String clinicAdminName;
    // Nested lists
    private List<SpecialtyDTO> specialties;
    private List<DoctorDTO> doctors;
}
