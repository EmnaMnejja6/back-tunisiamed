package com.example.projet.dto;
import java.util.List;

import lombok.Data;

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
    private List<ClinicSpecialtyDTO> clinicSpecialities;
    private List<ReviewDTO> reviews;
    
}
