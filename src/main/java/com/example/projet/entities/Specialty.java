package com.example.projet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.List;
import jakarta.persistence.OneToMany;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String label;
    @Column(columnDefinition="TEXT")
    private String description;
    private String iconUrl;
    @OneToMany(mappedBy="specialty")
    private List<ClinicSpecialty> clinicSpecialties;
    @OneToMany(mappedBy="specialty")
    private List<Doctor> doctors;
    @OneToMany(mappedBy="specialty")
    private List<QuoteRequest> quoteRequests;

}
