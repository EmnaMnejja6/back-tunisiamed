package com.example.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projet.entities.QuoteRequest;
import com.example.projet.entities.enums.QuoteStatus;
import com.example.projet.services.IQuoteRequestService;
import java.util.List;
import java.util.stream.Collectors;

import com.example.projet.mappers.QuoteRequestMapper;
import com.example.projet.dto.CreateQuoteRequestRequest;
import com.example.projet.dto.QuoteRequestDTO;


@RestController
@RequestMapping("/api/quote-requests")
public class QuoteRequestController {

    @Autowired
    private IQuoteRequestService quoteRequestService;

    @Autowired
    private QuoteRequestMapper quoteRequestMapper;

    // GET /api/quote-requests
    @GetMapping
    public ResponseEntity<List<QuoteRequestDTO>> getAll(
            @RequestParam(required = false) QuoteStatus status
    ) {
        List<QuoteRequest> list = (status != null)
                ? quoteRequestService.getQuoteRequestsByStatus(status)
                : quoteRequestService.getQuoteRequests();

        List<QuoteRequestDTO> dtos = list.stream()
                .map(quoteRequestMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET /api/quote-requests/{id}
    @GetMapping("/{id}")
    public ResponseEntity<QuoteRequestDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(quoteRequestMapper.toDTO(quoteRequestService.getQuoteRequestById(id)));
    }

    // GET /api/quote-requests/token/{token}
    @GetMapping("/token/{token}")
    public ResponseEntity<QuoteRequestDTO> getByToken(@PathVariable String token) {
        return ResponseEntity.ok(quoteRequestMapper.toDTO(quoteRequestService.getQuoteRequestByToken(token)));
    }

    // POST /api/quote-requests?specialtyId=1
    @PostMapping
    public ResponseEntity<QuoteRequestDTO> submit(@RequestBody CreateQuoteRequestRequest request) {
        QuoteRequest entity = quoteRequestMapper.toEntity(request);
        QuoteRequest saved = quoteRequestService.submit(entity, request.getSpecialtyId());
        return ResponseEntity.status(HttpStatus.CREATED).body(quoteRequestMapper.toDTO(saved));
    }

    // PATCH /api/quote-requests/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<QuoteRequestDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam QuoteStatus status
    ) {
        return ResponseEntity.ok(quoteRequestMapper.toDTO(quoteRequestService.updateStatus(id, status)));
    }

    // PATCH /api/quote-requests/{id}/close
    @PatchMapping("/{id}/close")
    public ResponseEntity<Void> close(@PathVariable Long id) {
        quoteRequestService.close(id);
        return ResponseEntity.noContent().build();
    }
}
