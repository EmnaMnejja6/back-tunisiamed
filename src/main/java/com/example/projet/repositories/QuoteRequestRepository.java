package com.example.projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projet.entities.QuoteRequest;
import java.util.List;
import java.util.Optional;

public interface QuoteRequestRepository extends JpaRepository<QuoteRequest, Long> {
    Optional<QuoteRequest> findByToken(String token);
    public List<QuoteRequest> findByStatus(com.example.projet.entities.enums.QuoteStatus status);
    public List<QuoteRequest> findByEmail(String email);
    public List<QuoteRequest> findBySpecialtyId(Long specialtyId);
}
