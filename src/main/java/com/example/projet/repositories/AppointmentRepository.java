package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.projet.entities.Appointment;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.quote.patient.id = :patientId")
    List<Appointment> findByQuotePatientId(@Param("patientId") Long patientId);
    @Query("SELECT a FROM Appointment a WHERE a.quote.clinic.id = :clinicId")
    List<Appointment> findByQuoteClinicId(@Param("clinicId") Long clinicId);
}
