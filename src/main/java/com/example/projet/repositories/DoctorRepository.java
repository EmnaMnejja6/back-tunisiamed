package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projet.entities.Doctor; 
import java.util.List; 

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    public List<Doctor> findByClinicId(Long clinicId);
    public List<Doctor> findBySpecialtyId(Long specialtyId);
    public List<Doctor> findByClinicIdAndSpecialtyId(Long clinicId, Long specialtyId);
}
