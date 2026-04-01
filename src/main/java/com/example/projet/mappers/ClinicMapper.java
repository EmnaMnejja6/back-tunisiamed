package com.example.projet.mappers;

import com.example.projet.dto.ClinicDTO;
import com.example.projet.dto.ClinicSpecialtyDTO;
import com.example.projet.dto.DoctorDTO;
import com.example.projet.dto.ReviewDTO;
import com.example.projet.entities.Clinic;
import com.example.projet.entities.ClinicSpecialty;
import com.example.projet.entities.Doctor;
import com.example.projet.entities.Review;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class ClinicMapper {

    public ClinicDTO toDTO(Clinic clinic) {
        ClinicDTO dto = new ClinicDTO();
        dto.setId(clinic.getId());
        dto.setName(clinic.getName());
        dto.setDescription(clinic.getDescription());
        dto.setAddress(clinic.getAddress());
        dto.setCity(clinic.getCity());
        dto.setLatitude(clinic.getLatitude());
        dto.setLongitude(clinic.getLongitude());
        dto.setPhone(clinic.getPhone());
        dto.setEmail(clinic.getEmail());

        if (clinic.getClinicSpecialities() != null) {
            dto.setClinicSpecialities(
                clinic.getClinicSpecialities().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList())
            );
        }

        if (clinic.getReviews() != null) {
            dto.setReviews(
                clinic.getReviews().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public ClinicSpecialtyDTO toDTO(ClinicSpecialty cs) {
        ClinicSpecialtyDTO dto = new ClinicSpecialtyDTO();
        dto.setId(cs.getId());
        dto.setSpecialtyTypeName(cs.getSpecialtyType().getName());
        dto.setBasePriceEstimate(cs.getBasePriceEstimate());
        dto.setRecommendedMinDays(cs.getRecommendedMinDays());

        if (cs.getDoctors() != null) {
            dto.setDoctors(
                cs.getDoctors().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public DoctorDTO toDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        dto.setPhotoUrl(doctor.getPhotoUrl());
        dto.setExperienceYears(doctor.getExperienceYears());
        dto.setDiploma(doctor.getDiploma());
        dto.setBiography(doctor.getBiography());
        return dto;
    }

    public ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }
}
