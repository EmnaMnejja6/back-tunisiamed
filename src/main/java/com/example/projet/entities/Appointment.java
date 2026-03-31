package com.example.projet.entities;

import org.hibernate.annotations.CreationTimestamp;

import com.example.projet.entities.enums.AppointmentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.EnumType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private LocalDateTime appointmentDate;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Payment payment;
    @OneToOne(mappedBy="appointment")
    private Quote quote;
    @OneToMany
    @JoinColumn(name="appointment_id")
    private List<Review> reviews;
}
