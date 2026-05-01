package com.example.projet.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private Integer rating;
    private LocalDateTime createdAt;
    private Long clinicId;
    private String clinicName;
}
