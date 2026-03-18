package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projet.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
