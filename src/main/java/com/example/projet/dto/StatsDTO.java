package com.example.projet.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class StatsDTO {
    private Long totalClinics;
    private Long totalPatients;
    private Long totalQuotes;
    private Long totalAppointments;
    private Long totalPayments;
    private BigDecimal totalRevenue;
    private BigDecimal totalCommissions;
}
