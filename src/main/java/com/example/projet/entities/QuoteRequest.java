package com.example.projet.entities;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.projet.entities.enums.QuoteStatus;
import jakarta.persistence.EnumType;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String country;
    private LocalDate dateofBirth;
    private String description;
    @ManyToOne
    private Specialty specialty;
    @OneToMany(mappedBy="quoteRequest")
    private List<QuoteResponse> quoteResponses;
    private LocalDateTime createdAt;
    private String token;

    @Enumerated(EnumType.STRING)
    private QuoteStatus status;

}
