package com.example.projet.controllers;


import com.example.projet.entities.QuoteResponse;
import com.example.projet.entities.enums.QuoteStatus;
import com.example.projet.services.IQuoteResponseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.projet.mappers.QuoteResponseMapper;
import com.example.projet.dto.CreateQuoteResponseRequest;
import com.example.projet.dto.QuoteResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quote-responses")
public class QuoteResponseController {

    @Autowired
    private IQuoteResponseService quoteResponseService;

    @Autowired
    private QuoteResponseMapper quoteResponseMapper;

    // GET /api/quote-responses/request/{quoteRequestId}
    @GetMapping("/request/{quoteRequestId}")
    public ResponseEntity<List<QuoteResponseDTO>> getByRequest(@PathVariable Long quoteRequestId) {
        List<QuoteResponseDTO> dtos = quoteResponseService.getQuoteResponsesByQuoteRequest(quoteRequestId)
                .stream().map(quoteResponseMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET /api/quote-responses/clinic/{clinicId}
    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<QuoteResponseDTO>> getByClinic(
            @PathVariable Long clinicId,
            @RequestParam(required = false) QuoteStatus status
    ) {
        List<QuoteResponse> list = (status != null)
                ? quoteResponseService.getQuoteResponsesByClinicAndStatus(clinicId, status)
                : quoteResponseService.getQuoteResponsesByClinic(clinicId);

        List<QuoteResponseDTO> dtos = list.stream()
                .map(quoteResponseMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    // GET /api/quote-responses/token/{token}
    @GetMapping("/token/{token}")
    public ResponseEntity<QuoteResponseDTO> getByToken(@PathVariable String token) {
        return ResponseEntity.ok(quoteResponseMapper.toDTO(quoteResponseService.getQuoteResponseByToken(token)));
    }
    // GET /api/quote-responses/{id}
    @GetMapping("/{id}")
    public ResponseEntity<QuoteResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(quoteResponseMapper.toDTO(quoteResponseService.getQuoteResponseById(id)));
    }

    // POST /api/quote-responses?quoteRequestId=1&clinicId=2
    @PostMapping
    public ResponseEntity<QuoteResponseDTO> respond(@RequestBody CreateQuoteResponseRequest request) {
        QuoteResponse entity = quoteResponseMapper.toEntity(request);
        QuoteResponse saved = quoteResponseService.respond(
                request.getQuoteRequestId(),
                request.getClinicId(),
                entity
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(quoteResponseMapper.toDTO(saved));
    }

    // PATCH /api/quote-responses/{id}/view
    @PatchMapping("/{id}/view")
    public ResponseEntity<QuoteResponseDTO> markAsViewed(@PathVariable Long id) {
        return ResponseEntity.ok(quoteResponseMapper.toDTO(quoteResponseService.markAsViewed(id)));
    }

    // PATCH /api/quote-responses/{id}/accept
    @PatchMapping("/{id}/accept")
    public ResponseEntity<QuoteResponseDTO> accept(@PathVariable Long id) {
        return ResponseEntity.ok(quoteResponseMapper.toDTO(quoteResponseService.accept(id)));
    }


}
