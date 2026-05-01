package com.example.projet.mappers;

import com.example.projet.dto.CreateDoctorRequest;
import com.example.projet.dto.DoctorDTO;
import com.example.projet.entities.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    @Autowired
    private SpecialtyMapper specialtyMapper;

    public DoctorDTO toDTO(Doctor doctor) {
        if (doctor == null) return null;
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        dto.setPhotoUrl(doctor.getPhotoUrl());
        dto.setExperienceYears(doctor.getExperienceYears());
        dto.setDiploma(doctor.getDiploma());
        dto.setBiography(doctor.getBiography());
        if (doctor.getClinic() != null) {
            dto.setClinicId(doctor.getClinic().getId());
            dto.setClinicName(doctor.getClinic().getName());
        }
        dto.setSpecialty(specialtyMapper.toDTO(doctor.getSpecialty()));
        return dto;
    }

    public Doctor toEntity(CreateDoctorRequest request) {
        if (request == null) return null;
        Doctor doctor = new Doctor();
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setPhotoUrl(request.getPhotoUrl());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setDiploma(request.getDiploma());
        doctor.setBiography(request.getBiography());
        // clinic and specialty are set in the service
        return doctor;
    }
}
