package com.example.projet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projet.entities.Clinic;
import com.example.projet.entities.ClinicSpecialty;
import com.example.projet.repositories.ClinicRepository;
import com.example.projet.entities.Specialty;

import java.util.List;
import com.example.projet.repositories.SpecialtyRepository;
import com.example.projet.repositories.ClinicSpecialtyRepository;
import com.example.projet.repositories.ReviewRepository;
import com.example.projet.entities.User;
import com.example.projet.entities.enums.Role;
import com.example.projet.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class ClinicServiceImpl implements IClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private ClinicSpecialtyRepository clinicSpecialtyRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public List<Clinic> getAllClinics(){
        return clinicRepository.findAll();
    }

    public Clinic getClinicById(Long id){
        return clinicRepository.findById(id).orElse(null);
    }

    public List<Clinic> getClinicByCity(String city) {
        return clinicRepository.findByCity(city);
    }

    public List<Clinic> getClinicBySpecialty(Long specialtyId) {
        return clinicRepository.findBySpecialtyId(specialtyId);
    }

    public List<Clinic> getClinicByAdminId(Long adminId) {
        return clinicRepository.findByClinicAdminId(adminId);
    }

    public List<Clinic> searchByKeyword(String keyword) {
        return clinicRepository.searchByKeyword(keyword);
    }


    public Clinic createClinic(Clinic clinic, Long adminId) {

        User admin = userRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin user not found"));

        if (admin.getRole() != Role.CLINIC_ADMIN) {
            throw new IllegalArgumentException("User is not a clinic admin");
        }

        clinic.setClinicAdmin(admin);

        return clinicRepository.save(clinic);
    }

    public Clinic updateClinic(Long id, Clinic clinic) {
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
        return clinicRepository.save(existing);
    }

    public void deleteClinic(Long id){
        clinicRepository.deleteById(id);
    }

    public Clinic addSpecialty(Long clinicId, Long specialtyId) {
        if (clinicSpecialtyRepository.existsByClinicIdAndSpecialtyId(clinicId, specialtyId)) {
            throw new IllegalArgumentException("Specialty already added to this clinic");
        }
        Clinic clinic = clinicRepository.findById(clinicId).orElse(null);
        Specialty specialty = specialtyRepository.findById(specialtyId).orElse(null);

        ClinicSpecialty cs = new ClinicSpecialty();
        cs.setClinic(clinic);
        cs.setSpecialty(specialty);
        clinicSpecialtyRepository.save(cs);

        return clinic;
    }

    @Transactional
    public void removeSpecialty(Long clinicId, Long specialtyId) {
        clinicSpecialtyRepository.deleteByClinicIdAndSpecialtyId(clinicId, specialtyId);
    }

    public void updateRating(Long clinicId) {
        Double avg = reviewRepository.findAverageRatingByClinicId(clinicId);
        if (avg != null) {
            Clinic clinic = clinicRepository.findById(clinicId).orElse(null);
            if (clinic != null) {
                clinic.setRating(avg);
                clinicRepository.save(clinic);
            }
        }
    }

}
