package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projet.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
