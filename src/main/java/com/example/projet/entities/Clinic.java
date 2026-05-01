package com.example.projet.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition="TEXT")
    private String description;
    @Column(columnDefinition="TEXT")
    private String address;
    private String city;
    private Double latitude;
    private Double longitude;
    private String phone;
    private String email;
    private String image_url;
    private Double rating;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "clinic_admin_id", nullable = false)
    private User clinicAdmin;
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    private List<ClinicSpecialty> clinicSpecialities;
    @OneToMany(mappedBy="clinic")
    private List<Review> reviews;
    @OneToMany(mappedBy="clinic")
    private List<QuoteResponse> quote_responses;
    @OneToMany(mappedBy="clinic")
    private List<Doctor> doctors;
}
