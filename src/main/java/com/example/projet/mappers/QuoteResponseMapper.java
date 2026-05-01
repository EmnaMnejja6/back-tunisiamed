package com.example.projet.mappers;

import com.example.projet.dto.CreateQuoteResponseRequest;
import com.example.projet.dto.QuoteResponseDTO;
import com.example.projet.entities.QuoteResponse;
import org.springframework.stereotype.Component;

@Component
public class QuoteResponseMapper {

    public QuoteResponseDTO toDTO(QuoteResponse qr) {
        if (qr == null) return null;
        QuoteResponseDTO dto = new QuoteResponseDTO();
        dto.setId(qr.getId());
        dto.setEstimatedPrice(qr.getEstimatedPrice());
        dto.setMessage(qr.getMessage());
        dto.setStatus(qr.getStatus());
        dto.setCreatedAt(qr.getCreatedAt());

        if (qr.getClinic() != null) {
            dto.setClinicId(qr.getClinic().getId());
            dto.setClinicName(qr.getClinic().getName());
            dto.setClinicCity(qr.getClinic().getCity());
            dto.setClinicImageUrl(qr.getClinic().getImage_url());
            dto.setClinicRating(qr.getClinic().getRating());
        }

        if (qr.getQuoteRequest() != null) {
            dto.setQuoteRequestId(qr.getQuoteRequest().getId());
        }

        return dto;
    }

    public QuoteResponse toEntity(CreateQuoteResponseRequest request) {
        if (request == null) return null;
        QuoteResponse response = new QuoteResponse();
        response.setEstimatedPrice(request.getEstimatedPrice());
        response.setMessage(request.getMessage());
        // clinic, quoteRequest, status are set in the service
        return response;
    }
}
