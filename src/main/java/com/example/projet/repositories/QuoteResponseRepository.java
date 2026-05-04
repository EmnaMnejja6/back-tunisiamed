package com.example.projet.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projet.entities.QuoteResponse;
import java.util.List;
import java.util.Optional;
import com.example.projet.entities.enums.QuoteStatus;

public interface QuoteResponseRepository extends JpaRepository<QuoteResponse, Long> {
    public List<QuoteResponse> findByQuoteRequestId(Long quoteRequestId);
    public List<QuoteResponse> findByClinicId(Long clinicId);
    public List<QuoteResponse> findByClinicIdAndStatus(Long clinicId, QuoteStatus status);
    public boolean existsByQuoteRequestIdAndClinicId(Long quoteRequestId, Long clinicId);
    public Optional<QuoteResponse> findByToken(String token);

}
