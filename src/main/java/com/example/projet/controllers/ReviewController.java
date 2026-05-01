package com.example.projet.controllers;

import com.example.projet.entities.Review;
import com.example.projet.services.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.projet.mappers.ReviewMapper;
import com.example.projet.dto.CreateReviewRequest;
import com.example.projet.dto.ReviewDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @Autowired
    private ReviewMapper reviewMapper;

    // GET /api/reviews/clinic/{clinicId}
    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<ReviewDTO>> getByClinic(@PathVariable Long clinicId) {
        List<ReviewDTO> dtos = reviewService.getReviewsByClinic(clinicId).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET /api/reviews/clinic/{clinicId}/average
    @GetMapping("/clinic/{clinicId}/average")
    public ResponseEntity<Map<String, Double>> getAverage(@PathVariable Long clinicId) {
        Double avg = reviewService.getAverageRating(clinicId);
        return ResponseEntity.ok(Map.of("average", avg != null ? avg : 0.0));
    }


    // POST /api/reviews?clinicId=1
    @PostMapping
    public ResponseEntity<ReviewDTO> create(@RequestBody CreateReviewRequest request) {
        Review review = reviewMapper.toEntity(request);
        Review saved = reviewService.create(review, request.getClinicId());
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewMapper.toDTO(saved));
    }

    // DELETE /api/reviews/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
