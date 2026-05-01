package com.example.projet.mappers;

import com.example.projet.dto.CreateQuoteRequestRequest;
import com.example.projet.dto.QuoteRequestDTO;
import com.example.projet.entities.QuoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuoteRequestMapper {

    @Autowired
    private SpecialtyMapper specialtyMapper;

    public QuoteRequestDTO toDTO(QuoteRequest qr) {
        if (qr == null) return null;
        QuoteRequestDTO dto = new QuoteRequestDTO();
        dto.setId(qr.getId());
        dto.setFname(qr.getFname());
        dto.setLname(qr.getLname());
        dto.setEmail(qr.getEmail());
        dto.setPhone(qr.getPhone());
        dto.setCountry(qr.getCountry());
        dto.setDateofBirth(qr.getDateofBirth());
        dto.setDescription(qr.getDescription());
        dto.setStatus(qr.getStatus());
        dto.setToken(qr.getToken());
        dto.setCreatedAt(qr.getCreatedAt());
        dto.setSpecialty(specialtyMapper.toDTO(qr.getSpecialty()));
        return dto;
    }

    public QuoteRequest toEntity(CreateQuoteRequestRequest request) {
        if (request == null) return null;
        QuoteRequest qr = new QuoteRequest();
        qr.setFname(request.getFname());
        qr.setLname(request.getLname());
        qr.setEmail(request.getEmail());
        qr.setPhone(request.getPhone());
        qr.setCountry(request.getCountry());
        qr.setDateofBirth(request.getDateofBirth());
        qr.setDescription(request.getDescription());
        // specialty and token/status are set in the service
        return qr;
    }
}
