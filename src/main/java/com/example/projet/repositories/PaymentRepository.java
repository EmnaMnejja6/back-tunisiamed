package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.projet.entities.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
     @Query("SELECT p FROM Payment p WHERE p.appointment.quote.patient.id = :patientId")
    List<Payment> findByAppointmentQuotePatientId(@Param("patientId") Long patientId);

    @Query("SELECT p FROM Payment p WHERE p.appointment.quote.clinic.id = :clinicId")
    List<Payment> findByAppointmentQuoteClinicId(@Param("clinicId") Long clinicId);
}
