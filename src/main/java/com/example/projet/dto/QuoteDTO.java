package com.example.projet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.projet.entities.enums.QuoteStatus;

import lombok.Data;

@Data
public class QuoteDTO {
    private Long id;
    private Long patientId;
    private String patientName;
    private Long clinicId;
    private String clinicName;
    private String specialtyName;
    private Integer durationDays;
    private String hotelType;
    private Boolean includeTransport;
    private BigDecimal medicalCostEstimate;
    private BigDecimal hotelCostEstimate;
    private BigDecimal transportCostEstimate;
    private BigDecimal totalEstimatedCost;
    private BigDecimal finalApprovedCost;
    private QuoteStatus status;
    private LocalDateTime createdAt;
}