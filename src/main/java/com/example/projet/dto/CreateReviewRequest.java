package com.example.projet.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CreateReviewRequest {
    private Long clinicId;

    @Min(1)
    @Max(5)
    private Integer rating;
}
