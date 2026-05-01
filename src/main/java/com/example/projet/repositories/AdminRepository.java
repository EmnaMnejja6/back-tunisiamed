package com.example.projet.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projet.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
