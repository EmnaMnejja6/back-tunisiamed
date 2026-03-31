package com.example.projet.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    private Integer totalBookings;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    private List<ClinicSpecialty> clinicSpecialities;
    @OneToMany(mappedBy="clinic")
    private List<Review> reviews;
    @OneToMany(mappedBy="clinic")
    private List<Quote> quotes;
}
