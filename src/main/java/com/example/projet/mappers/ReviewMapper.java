package com.example.projet.mappers;

import org.springframework.stereotype.Component;
import com.example.projet.dto.ReviewDTO;
import com.example.projet.entities.Review;

@Component
public class ReviewMapper {
    public ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setPatientName(review.getPatient().getFirstName() + " " + review.getPatient().getLastName());
        dto.setClinicId(review.getClinic().getId());
        return dto;
    }
}
