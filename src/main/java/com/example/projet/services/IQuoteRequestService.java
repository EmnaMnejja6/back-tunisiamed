package com.example.projet.services;

import com.example.projet.entities.QuoteRequest;
import com.example.projet.entities.enums.QuoteStatus;
import java.util.List;

public interface IQuoteRequestService {
    public QuoteRequest submit(QuoteRequest quoteRequest, Long specialtyId);
    public QuoteRequest getQuoteRequestById(Long id);
    public QuoteRequest getQuoteRequestByToken(String token);
    public List<QuoteRequest> getQuoteRequests();
    public List<QuoteRequest> getQuoteRequestsByStatus(QuoteStatus status);
    public QuoteRequest updateStatus(Long id, QuoteStatus status);
    public void close(Long id); 
}
