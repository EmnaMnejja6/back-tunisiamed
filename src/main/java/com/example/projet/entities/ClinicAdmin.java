package com.example.projet.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.example.projet.entities.enums.Role;

@Entity
@Table(name = "clinic_admins")
@Data
@AllArgsConstructor

public class ClinicAdmin extends User {

    @OneToMany(mappedBy = "clinicAdmin")
    private List<Clinic> clinics;
    public ClinicAdmin() {
        this.setRole(Role.CLINIC_ADMIN);
    }
}
