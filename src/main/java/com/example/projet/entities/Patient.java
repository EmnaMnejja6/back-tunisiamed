package com.example.projet.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends User {

    @Column(length = 100)
    private String nationality;

    @Column(length = 50)
    private String passportNumber;

    @OneToMany(mappedBy = "patient")
    private List<Quote> quotes;

    @OneToMany(mappedBy = "patient")
    private List<Review> reviews;
}

