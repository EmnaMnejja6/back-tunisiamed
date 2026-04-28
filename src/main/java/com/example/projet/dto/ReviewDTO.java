package com.example.projet.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private String comment;
    private Integer rating;
    private LocalDateTime createdAt;
    private String patientName;
    private Long clinicId;
}
