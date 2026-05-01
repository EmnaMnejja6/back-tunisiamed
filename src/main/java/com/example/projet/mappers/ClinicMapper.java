package com.example.projet.mappers;

import com.example.projet.dto.ClinicDTO;
import com.example.projet.dto.CreateClinicRequest;
import com.example.projet.entities.Clinic;
import com.example.projet.entities.ClinicSpecialty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class ClinicMapper {

    @Autowired
    private SpecialtyMapper specialtyMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    public ClinicDTO toDTO(Clinic clinic) {
        if (clinic == null) return null;
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
        dto.setImageUrl(clinic.getImage_url());
        dto.setRating(clinic.getRating());
        dto.setCreatedAt(clinic.getCreatedAt());

        if (clinic.getClinicAdmin() != null) {
            dto.setClinicAdminId(clinic.getClinicAdmin().getId());
            dto.setClinicAdminName(
                clinic.getClinicAdmin().getFirstName() + " " + clinic.getClinicAdmin().getLastName()
            );
        }

        // Map specialties from ClinicSpecialty join entity
        if (clinic.getClinicSpecialities() != null) {
            dto.setSpecialties(
                clinic.getClinicSpecialities().stream()
                    .map(ClinicSpecialty::getSpecialty)
                    .map(specialtyMapper::toDTO)
                    .collect(Collectors.toList())
            );
        } else {
            dto.setSpecialties(Collections.emptyList());
        }

        // Map doctors
        if (clinic.getDoctors() != null) {
            dto.setDoctors(
                clinic.getDoctors().stream()
                    .map(doctorMapper::toDTO)
                    .collect(Collectors.toList())
            );
        } else {
            dto.setDoctors(Collections.emptyList());
        }

        return dto;
    }

    public Clinic toEntity(CreateClinicRequest request) {
        if (request == null) return null;
        Clinic clinic = new Clinic();
        clinic.setName(request.getName());
        clinic.setDescription(request.getDescription());
        clinic.setAddress(request.getAddress());
        clinic.setCity(request.getCity());
        clinic.setLatitude(request.getLatitude());
        clinic.setLongitude(request.getLongitude());
        clinic.setPhone(request.getPhone());
        clinic.setEmail(request.getEmail());
        clinic.setImage_url(request.getImageUrl());
        // clinicAdmin is set in the service using clinicAdminId
        return clinic;
    }
}
