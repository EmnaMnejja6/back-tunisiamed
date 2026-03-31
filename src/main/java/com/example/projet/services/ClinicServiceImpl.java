package com.example.projet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projet.entities.Clinic;
import com.example.projet.entities.ClinicSpecialty;
import com.example.projet.repositories.ClinicRepository;
import com.example.projet.entities.Review;
import com.example.projet.entities.SpecialtyType;

import java.util.List;
import com.example.projet.entities.Doctor;
import com.example.projet.repositories.DoctorRepository;
import com.example.projet.repositories.SpecialtyTypeRepository;
import com.example.projet.repositories.ClinicSpecialtyRepository;

@Service
public class ClinicServiceImpl implements IClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ClinicSpecialtyRepository clinicSpecialtyRepository;
    
    @Autowired
    private SpecialtyTypeRepository specialtyTypeRepository;
    
    public List<Clinic> getAllClinics(String city, Long specialtyId){
        if (city != null && specialtyId != null) {
            return clinicRepository.findByCityAndClinicSpecialitiesSpecialtyTypeId(city, specialtyId);
        }else if (city != null) {
            return clinicRepository.findByCity(city);
        }else if (specialtyId != null) {
            return clinicRepository.findByClinicSpecialitiesSpecialtyTypeId(specialtyId);
        }else{
            return clinicRepository.findAll();
        }
    }

    public Clinic getClinicById(Long id){
        return clinicRepository.findById(id).orElse(null);
    }

    public List<Doctor> getDoctorsByClinicId(Long clinicId){
        return clinicRepository.findDoctorsByClinicId(clinicId);
    }

    public List<Review> getReviewsByClinicId(Long clinicId){
        Clinic clinic = clinicRepository.findById(clinicId).orElse(null);
        if (clinic != null) {
            return clinic.getReviews();
        }else{return null;}
    }

    public List<ClinicSpecialty> getSpecialtiesByClinicId(Long clinicId){
        Clinic clinic = clinicRepository.findById(clinicId).orElse(null);
        if (clinic != null) {
            return clinic.getClinicSpecialities();
        } else {
            return null;
        }
    }

    public void addClinic(Clinic clinic){
        clinicRepository.save(clinic);
    }

    public void updateClinic(Long id, Clinic clinic) {
        Clinic existing = clinicRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Clinic not found"));
        existing.setName(clinic.getName());
        existing.setAddress(clinic.getAddress());
        existing.setCity(clinic.getCity());
        existing.setPhone(clinic.getPhone());
        existing.setDescription(clinic.getDescription());
        existing.setEmail(clinic.getEmail());
        existing.setLatitude(clinic.getLatitude());
        existing.setLongitude(clinic.getLongitude());
        clinicRepository.save(existing);
    }

    public void deleteClinic(Long id){
        clinicRepository.deleteById(id);
    }

    

    public Doctor getDoctorById(Long clinicId, Long doctorId) {
    return doctorRepository.findByIdAndClinicId(doctorId, clinicId)
        .orElseThrow(() -> new RuntimeException("Doctor not found in this clinic"));
    }

    public void addDoctor(Long clinicId, Doctor doctor) {
        ClinicSpecialty clinicSpecialty = clinicSpecialtyRepository
        .findById(doctor.getClinicSpecialty().getId())
        .orElseThrow(() -> new RuntimeException("ClinicSpecialty not found"));

        if (!clinicSpecialty.getClinic().getId().equals(clinicId))
            throw new RuntimeException("ClinicSpecialty does not belong to this clinic");

        doctorRepository.save(doctor);
    }
    public void updateDoctor(Long clinicId,Long doctorId, Doctor doctor) {
        Doctor existing_doctor = doctorRepository.findById(doctorId)
        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (!existing_doctor.getClinicSpecialty().getClinic().getId().equals(clinicId))
            throw new RuntimeException("Doctor does not belong to this clinic");

        existing_doctor.setFirstName(doctor.getFirstName());
        existing_doctor.setLastName(doctor.getLastName());
        existing_doctor.setPhotoUrl(doctor.getPhotoUrl());
        existing_doctor.setExperienceYears(doctor.getExperienceYears());
        existing_doctor.setDiploma(doctor.getDiploma());
        existing_doctor.setBiography(doctor.getBiography());
        doctorRepository.save(existing_doctor);
    }
    public void deleteDoctor(Long clinicId, Long id) {
        Doctor doctor = doctorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (!doctor.getClinicSpecialty().getClinic().getId().equals(clinicId))
            throw new RuntimeException("Doctor does not belong to this clinic");
        doctorRepository.deleteById(id);
    }

    
    public void addSpecialtyToClinic(Long clinicId, Long specialtyTypeId, ClinicSpecialty clinicSpecialty) {
        Clinic clinic = clinicRepository.findById(clinicId)
            .orElseThrow(() -> new RuntimeException("Clinic not found"));
        SpecialtyType specialtyType = specialtyTypeRepository.findById(specialtyTypeId)
            .orElseThrow(() -> new RuntimeException("SpecialtyType not found"));

        clinicSpecialty.setClinic(clinic);
        clinicSpecialty.setSpecialtyType(specialtyType);
        clinicSpecialtyRepository.save(clinicSpecialty);
    }

    public void updateClinicSpecialty(Long clinicId, Long csId, ClinicSpecialty clinicSpecialty) {
        ClinicSpecialty existing = clinicSpecialtyRepository.findById(csId)
            .orElseThrow(() -> new RuntimeException("ClinicSpecialty not found"));

        if (!existing.getClinic().getId().equals(clinicId))
            throw new RuntimeException("ClinicSpecialty does not belong to this clinic");

        existing.setBasePriceEstimate(clinicSpecialty.getBasePriceEstimate());
        existing.setRecommendedMinDays(clinicSpecialty.getRecommendedMinDays());
        clinicSpecialtyRepository.save(existing);
    }

    public void removeSpecialtyFromClinic(Long clinicId, Long csId) {
        ClinicSpecialty existing = clinicSpecialtyRepository.findById(csId)
            .orElseThrow(() -> new RuntimeException("ClinicSpecialty not found"));

        if (!existing.getClinic().getId().equals(clinicId))
            throw new RuntimeException("ClinicSpecialty does not belong to this clinic");

        clinicSpecialtyRepository.deleteById(csId);
    }

}
