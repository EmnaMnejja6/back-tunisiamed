package com.example.projet.mappers;

import org.springframework.stereotype.Component;

import com.example.projet.dto.PaymentDTO;
import com.example.projet.entities.Payment;

@Component
public class PaymentMapper {
    public PaymentDTO toDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setAmountTotal(payment.getAmountTotal());
        dto.setCommissionRate(payment.getCommissionRate());
        dto.setCommissionAmount(payment.getCommissionAmount());
        dto.setClinicAmount(payment.getClinicAmount());
        dto.setStatus(payment.getStatus());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAppointmentId(payment.getAppointment().getId());
        return dto;
    }
}
