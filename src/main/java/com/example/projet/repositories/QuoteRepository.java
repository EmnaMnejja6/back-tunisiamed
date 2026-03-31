package com.example.projet.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projet.entities.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

}
