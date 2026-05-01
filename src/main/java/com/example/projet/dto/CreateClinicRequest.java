package com.example.projet.dto;

import lombok.Data;

@Data
public class CreateClinicRequest {
    private String name;
    private String description;
    private String address;
    private String city;
    private Double latitude;
    private Double longitude;
    private String phone;
    private String email;
    private String imageUrl;
    private Long clinicAdminId;
}
