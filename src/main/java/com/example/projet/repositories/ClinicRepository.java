package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projet.entities.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

}
