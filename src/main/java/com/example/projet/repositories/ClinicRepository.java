package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.projet.entities.Clinic;
import com.example.projet.entities.Doctor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    public List<Clinic> findByCityAndClinicSpecialitiesSpecialtyTypeId(String city, Long specialtyId);
    public List<Clinic> findByCity(String city);
    public List<Clinic> findByClinicSpecialitiesSpecialtyTypeId(Long specialtyId);
    @Query("SELECT d FROM Doctor d WHERE d.clinicSpecialty.clinic.id = :clinicId")
    public List<Doctor> findDoctorsByClinicId(@Param("clinicId") Long clinicId);
}
