package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projet.entities.ClinicAdmin;
import java.util.Optional;

public interface ClinicAdminRepository extends JpaRepository<ClinicAdmin, Long> {
    public Optional<ClinicAdmin> findByEmail(String email);

}
