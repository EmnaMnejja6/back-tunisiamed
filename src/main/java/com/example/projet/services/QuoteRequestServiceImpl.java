package com.example.projet.services;

import org.springframework.stereotype.Service;

import com.example.projet.entities.QuoteRequest;
import com.example.projet.entities.Specialty;
import com.example.projet.entities.enums.QuoteStatus;
import com.example.projet.repositories.QuoteRequestRepository;
import com.example.projet.repositories.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.UUID;

@Service
public class QuoteRequestServiceImpl implements IQuoteRequestService {
    @Autowired
    private QuoteRequestRepository quoteRequestRepository;
    @Autowired
    private SpecialtyRepository specialtyRepository;

    public QuoteRequest submit(QuoteRequest quoteRequest, Long specialtyId){
        Specialty specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new RuntimeException("Specialty not found with id: " + specialtyId));
        quoteRequest.setSpecialty(specialty);
        quoteRequest.setStatus(QuoteStatus.PENDING);
        quoteRequest.setToken(UUID.randomUUID().toString());
        return quoteRequestRepository.save(quoteRequest);        

    }
    public QuoteRequest getQuoteRequestById(Long id){
        return quoteRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quote request not found with id: " + id));
    }
    public QuoteRequest getQuoteRequestByToken(String token){
        return quoteRequestRepository.findByToken(token);
    }
    public List<QuoteRequest> getQuoteRequests(){
        return quoteRequestRepository.findAll();
    }
    public List<QuoteRequest> getQuoteRequestsByStatus(QuoteStatus status){
        return quoteRequestRepository.findByStatus(status);
    }
    public QuoteRequest updateStatus(Long id, QuoteStatus status){
        QuoteRequest qr = quoteRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quote request not found with id: " + id));
        qr.setStatus(status);
        return quoteRequestRepository.save(qr);
    }
    public void close(Long id){
        QuoteRequest qr = quoteRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quote request not found with id: " + id));
        qr.setStatus(QuoteStatus.CLOSED);
        quoteRequestRepository.save(qr);
    }

}
