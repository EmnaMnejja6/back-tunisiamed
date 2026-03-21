package com.example.projet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projet.entities.Clinic;
import com.example.projet.entities.ClinicSpecialty;
import com.example.projet.repositories.ClinicRepository;
import com.example.projet.entities.Review;
import java.util.List;
import com.example.projet.entities.Doctor;

@Service
public class ClinicServiceImpl implements IClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

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

    public void updateClinic(Clinic clinic){
        if(clinicRepository.existsById(clinic.getId())){
            clinicRepository.save(clinic);
        }else{
            System.out.println("Clinic doesn't exist");
        }
    }

    public void deleteClinic(Long id){
        clinicRepository.deleteById(id);
    }

}
