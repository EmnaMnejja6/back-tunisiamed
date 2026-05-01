package com.example.projet.mappers;

import com.example.projet.dto.CreateReviewRequest;
import com.example.projet.dto.ReviewDTO;
import com.example.projet.entities.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toDTO(Review review) {
        if (review == null) return null;
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setCreatedAt(review.getCreatedAt());
        if (review.getClinic() != null) {
            dto.setClinicId(review.getClinic().getId());
            dto.setClinicName(review.getClinic().getName());
        }
        return dto;
    }

    public Review toEntity(CreateReviewRequest request) {
        if (request == null) return null;
        Review review = new Review();
        review.setRating(request.getRating());
        // clinic is set in the service
        return review;
    }
}
