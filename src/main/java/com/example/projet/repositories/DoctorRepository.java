package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.projet.entities.Doctor;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;   

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT d FROM Doctor d WHERE d.id = :doctorId AND d.clinicSpecialty.clinic.id = :clinicId")
    Optional<Doctor> findByIdAndClinicId(@Param("doctorId") Long doctorId, @Param("clinicId") Long clinicId);
}
