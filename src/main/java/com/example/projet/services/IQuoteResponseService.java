package com.example.projet.services;

import java.util.List;
import com.example.projet.entities.QuoteResponse;
import com.example.projet.entities.enums.QuoteStatus;

public interface IQuoteResponseService {
    public QuoteResponse respond(Long quoteRequestId, Long clinicId, QuoteResponse response);
    
    public QuoteResponse getQuoteResponseById(Long id);
    
    public List<QuoteResponse> getQuoteResponsesByQuoteRequest(Long quoteRequestId);
    
    public List<QuoteResponse> getQuoteResponsesByClinic(Long clinicId);
    
    public QuoteResponse markAsViewed(Long id);
    
    public QuoteResponse accept(Long id);
    
    public List<QuoteResponse> getQuoteResponsesByClinicAndStatus(Long clinicId, QuoteStatus status);
    
    // Changed return type from QuoteResponse to List<QuoteResponse>
    public List<QuoteResponse> getQuoteResponseByToken(String token);
}
