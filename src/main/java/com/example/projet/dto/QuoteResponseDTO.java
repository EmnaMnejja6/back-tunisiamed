package com.example.projet.dto;

import com.example.projet.entities.enums.QuoteStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class QuoteResponseDTO {
    private Long id;
    private BigDecimal estimatedPrice;
    private String message;
    private QuoteStatus status;
    private LocalDateTime createdAt;
    // Clinic info
    private Long clinicId;
    private String clinicName;
    private String clinicCity;
    private String clinicImageUrl;
    private Double clinicRating;
    // QuoteRequest reference
    private Long quoteRequestId;
}
