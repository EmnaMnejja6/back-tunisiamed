package com.example.projet.mappers;

import org.springframework.stereotype.Component;

import com.example.projet.dto.QuoteDTO;
import com.example.projet.entities.Quote;

@Component
public class QuoteMapper {
    public QuoteDTO toDTO(Quote quote) {
        QuoteDTO dto = new QuoteDTO();
        dto.setId(quote.getId());
        dto.setPatientId(quote.getPatient().getId());
        dto.setPatientName(quote.getPatient().getFirstName() + " " + quote.getPatient().getLastName());
        dto.setClinicId(quote.getClinic().getId());
        dto.setClinicName(quote.getClinic().getName());
        dto.setSpecialtyName(quote.getClinicSpecialty().getSpecialtyType().getName());
        dto.setDurationDays(quote.getDurationDays());
        dto.setHotelType(quote.getHotelType());
        dto.setIncludeTransport(quote.getIncludeTransport());
        dto.setMedicalCostEstimate(quote.getMedicalCostEstimate());
        dto.setHotelCostEstimate(quote.getHotelCostEstimate());
        dto.setTransportCostEstimate(quote.getTransportCostEstimate());
        dto.setTotalEstimatedCost(quote.getTotalEstimatedCost());
        dto.setFinalApprovedCost(quote.getFinalApprovedCost());
        dto.setStatus(quote.getStatus());
        dto.setCreatedAt(quote.getCreatedAt());
        return dto;
    }
}
