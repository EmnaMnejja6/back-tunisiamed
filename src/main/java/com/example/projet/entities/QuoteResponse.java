package com.example.projet.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.example.projet.entities.enums.QuoteStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Clinic clinic;

    @ManyToOne
    private QuoteRequest quoteRequest;

    @Column(precision = 10, scale = 2)
    private BigDecimal estimatedPrice;

    @Enumerated(EnumType.STRING)
    private QuoteStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
