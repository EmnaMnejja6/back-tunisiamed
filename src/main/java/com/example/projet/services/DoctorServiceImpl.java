package com.example.projet.services;

import org.springframework.stereotype.Service;

import com.example.projet.entities.Clinic;
import com.example.projet.entities.Doctor;
import com.example.projet.entities.Specialty;
import com.example.projet.repositories.ClinicRepository;
import com.example.projet.repositories.DoctorRepository;
import com.example.projet.repositories.SpecialtyRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class DoctorServiceImpl implements IDoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private SpecialtyRepository specialtyRepository;

    public Doctor create(Doctor doctor, Long clinicId, Long specialtyId){
        Clinic clinic = clinicRepository.findById(clinicId).orElseThrow(() -> new EntityNotFoundException("Clinic not found with id: " + clinicId));
        Specialty specialty = specialtyRepository.findById(specialtyId).orElseThrow(() -> new EntityNotFoundException("Specialty not found with id: " + specialtyId));
        doctor.setClinic(clinic);
        doctor.setSpecialty(specialty);
        return doctorRepository.save(doctor);
    }
    public Doctor update(Long id, Doctor doctor){
        Doctor existing = doctorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + id));
        existing.setFirstName(doctor.getFirstName());
        existing.setLastName(doctor.getLastName());
        existing.setPhotoUrl(doctor.getPhotoUrl());
        existing.setDiploma(doctor.getDiploma());
        existing.setExperienceYears(doctor.getExperienceYears());
        existing.setBiography(doctor.getBiography());
        return doctorRepository.save(existing);
    }
    public void delete(Long id){
        if (!doctorRepository.existsById(id)) {
            throw new EntityNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }
    public Doctor getDoctorById(Long id){
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + id));
    }
    public List<Doctor> getDoctorsByClinic(Long clinicId){
        return doctorRepository.findByClinicId(clinicId);
    }
    public List<Doctor> getDoctorsBySpecialty(Long specialtyId){
        return doctorRepository.findBySpecialtyId(specialtyId);
    }
    public List<Doctor> getDoctorsByClinicAndSpecialty(Long clinicId, Long specialtyId){
        return doctorRepository.findByClinicIdAndSpecialtyId(clinicId, specialtyId);
    }
}
