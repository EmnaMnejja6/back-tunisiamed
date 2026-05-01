package com.example.projet.services;

import java.util.List;
import com.example.projet.entities.Review;

public interface IReviewService {
    public Review create(Review review, Long clinicId);
    public List<Review> getReviewsByClinic(Long clinicId);
    public Double getAverageRating(Long clinicId);
    public void delete(Long id);
}