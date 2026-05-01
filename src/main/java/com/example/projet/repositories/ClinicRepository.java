package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.projet.entities.Clinic;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    @Query("SELECT c FROM Clinic c JOIN c.clinicSpecialities cs WHERE cs.specialty.id = :specialtyId")
    public List<Clinic> findBySpecialtyId(@Param("specialtyId") Long specialtyId);
    public List<Clinic> findByCity(String city);
    public List<Clinic> findByClinicAdminId(Long adminId);
    @Query("SELECT c FROM Clinic c WHERE " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "EXISTS (SELECT cs FROM ClinicSpecialty cs WHERE cs.clinic = c AND LOWER(cs.specialty.label) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    public List<Clinic> searchByKeyword(@Param("keyword") String keyword);


}