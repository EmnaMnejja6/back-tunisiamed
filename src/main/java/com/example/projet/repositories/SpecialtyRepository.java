package com.example.projet.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projet.entities.Specialty;
import java.util.Optional;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    public Optional<Specialty> findByLabel(String label);
    public boolean existsByLabel(String label);
}
