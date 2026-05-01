package com.example.projet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projet.entities.Clinic;
import com.example.projet.entities.Review;
import com.example.projet.repositories.ClinicRepository;
import com.example.projet.repositories.ReviewRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class ReviewServiceImpl implements IReviewService {
    
    @Autowired
    private ClinicServiceImpl clinicServiceImpl;
    @Autowired 
    private ReviewRepository reviewRepository;
    @Autowired 
    private ClinicRepository clinicRepository;



    public Review create(Review review, Long clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
            .orElseThrow(() -> new RuntimeException("Clinic not found with id: " + clinicId));
        review.setClinic(clinic);
        Review saved = reviewRepository.save(review);
        clinicServiceImpl.updateRating(clinicId);
        return saved;
    }

    public List<Review> getReviewsByClinic(Long clinicId) {
        return reviewRepository.findByClinicId(clinicId);
    }

    public Double getAverageRating(Long clinicId) {
        return reviewRepository.findAverageRatingByClinicId(clinicId);
    }


    public void delete(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
        Long clinicId = review.getClinic().getId();
        reviewRepository.deleteById(id);
        clinicServiceImpl.updateRating(clinicId);
    }
}
