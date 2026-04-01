package com.example.projet.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "quotes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "clinic_specialty_id", nullable = false)
    private ClinicSpecialty clinicSpecialty;

    @Column(nullable = false)
    private Integer durationDays;

    @Column(length = 50)
    private String hotelType;

    @Column(nullable = false)
    private Boolean includeTransport;

    @Column(precision = 10, scale = 2)
    private BigDecimal medicalCostEstimate;

    @Column(precision = 10, scale = 2)
    private BigDecimal hotelCostEstimate;

    @Column(precision = 10, scale = 2)
    private BigDecimal transportCostEstimate;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalEstimatedCost;

    @Column(precision = 10, scale = 2)
    private BigDecimal finalApprovedCost;

    @Enumerated(EnumType.STRING)
    private QuoteStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /*@OneToOne(mappedBy = "quote", cascade = CascadeType.ALL)
    private AiEstimationLog aiEstimationLog;*/

    @OneToOne(mappedBy = "quote")
    private Appointment appointment;
}
