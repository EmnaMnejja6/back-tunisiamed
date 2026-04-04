package com.example.projet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.projet.entities.enums.PaymentStatus;

import lombok.Data;

@Data
public class PaymentDTO {
    private Long id;
    private BigDecimal amountTotal;
    private BigDecimal commissionRate;
    private BigDecimal commissionAmount;
    private BigDecimal clinicAmount;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
    private Long appointmentId;
}
