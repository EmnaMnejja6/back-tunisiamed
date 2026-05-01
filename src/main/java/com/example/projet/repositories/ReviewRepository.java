package com.example.projet.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.projet.entities.Review;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> findByClinicId(Long clinicId);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.clinic.id = :clinicId")
    public Double findAverageRatingByClinicId(@Param("clinicId") Long clinicId);
}