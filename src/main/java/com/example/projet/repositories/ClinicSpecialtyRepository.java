package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projet.entities.ClinicSpecialty;
import java.util.List;

public interface ClinicSpecialtyRepository extends JpaRepository<ClinicSpecialty, Long> {
    public List<ClinicSpecialty> findByClinicId(Long clinicId);
    public List<ClinicSpecialty> findBySpecialtyId(Long specialtyId);
    public boolean existsByClinicIdAndSpecialtyId(Long clinicId, Long specialtyTypeId);
    public void deleteByClinicIdAndSpecialtyId(Long clinicId, Long specialtyTypeId);
}
