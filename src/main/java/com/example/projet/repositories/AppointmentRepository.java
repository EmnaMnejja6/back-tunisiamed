package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projet.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
