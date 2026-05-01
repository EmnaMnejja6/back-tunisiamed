package com.example.projet.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateQuoteResponseRequest {
    private Long quoteRequestId;
    private Long clinicId;
    private BigDecimal estimatedPrice;
    private String message;
}
