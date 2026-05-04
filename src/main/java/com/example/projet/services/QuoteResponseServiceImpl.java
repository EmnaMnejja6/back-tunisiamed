package com.example.projet.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.example.projet.entities.Clinic;
import com.example.projet.entities.QuoteRequest;
import com.example.projet.entities.QuoteResponse;
import com.example.projet.entities.enums.QuoteStatus;
import com.example.projet.repositories.ClinicRepository;
import com.example.projet.repositories.QuoteRequestRepository;
import com.example.projet.repositories.QuoteResponseRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class QuoteResponseServiceImpl implements IQuoteResponseService {

    @Autowired
    private QuoteResponseRepository quoteResponseRepository;

    @Autowired
    private QuoteRequestRepository quoteRequestRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    public QuoteResponse respond(Long quoteRequestId, Long clinicId, QuoteResponse response) {
        if (quoteResponseRepository.existsByQuoteRequestIdAndClinicId(quoteRequestId, clinicId)) {
            throw new IllegalArgumentException("This clinic already responded to this request");
        }

        QuoteRequest quoteRequest = quoteRequestRepository.findById(quoteRequestId)
            .orElseThrow(() -> new RuntimeException("Quote request not found with id: " + quoteRequestId));

        Clinic clinic = clinicRepository.findById(clinicId)
            .orElseThrow(() -> new RuntimeException("Clinic not found with id: " + clinicId));

        response.setQuoteRequest(quoteRequest);
        response.setClinic(clinic);
        response.setStatus(QuoteStatus.SENT);

        QuoteResponse saved = quoteResponseRepository.save(response);

        quoteRequest.setStatus(QuoteStatus.RESPONDED);
        quoteRequestRepository.save(quoteRequest);

        return saved;
    }

    public QuoteResponse getQuoteResponseById(Long id) {
        return quoteResponseRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("QuoteResponse not found with id: " + id));
    }

    public List<QuoteResponse> getQuoteResponsesByQuoteRequest(Long quoteRequestId) {
        return quoteResponseRepository.findByQuoteRequestId(quoteRequestId);
    }

    public List<QuoteResponse> getQuoteResponsesByClinic(Long clinicId) {
        return quoteResponseRepository.findByClinicId(clinicId);
    }

    public QuoteResponse markAsViewed(Long id) {
        QuoteResponse response = quoteResponseRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("QuoteResponse not found with id: " + id));
        response.setStatus(QuoteStatus.VIEWED);
        return quoteResponseRepository.save(response);
    }

    public QuoteResponse accept(Long id) {
        QuoteResponse response = quoteResponseRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("QuoteResponse not found with id: " + id));
        response.setStatus(QuoteStatus.ACCEPTED);

        QuoteRequest qr = response.getQuoteRequest();
        qr.setStatus(QuoteStatus.CLOSED);
        quoteRequestRepository.save(qr);

        return quoteResponseRepository.save(response);
    }

    public List<QuoteResponse> getQuoteResponsesByClinicAndStatus(Long clinicId, QuoteStatus status) {
        return quoteResponseRepository.findByClinicIdAndStatus(clinicId, status);
    }

    // FIXED: This method now looks for token in quote_request table, not quote_response table
    public List<QuoteResponse> getQuoteResponseByToken(String token) {
        // 1. Find the QuoteRequest by token (token is in quote_request table)
        QuoteRequest quoteRequest = quoteRequestRepository.findByToken(token)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Quote request not found with token: " + token
            ));
        
        // 2. Get all QuoteResponses for this QuoteRequest
        List<QuoteResponse> responses = quoteResponseRepository.findByQuoteRequestId(quoteRequest.getId());
        
        return responses;
    }
}
