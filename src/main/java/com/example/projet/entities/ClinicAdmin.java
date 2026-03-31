package com.example.projet.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clinic_admins")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClinicAdmin extends User {

    @OneToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
}
