package com.example.projet.entities;

import jakarta.persistence.*;
import lombok.*;
import com.example.projet.entities.enums.Role;


@Entity
@Table(name = "admins")
@Data
public class Admin extends User {
    public Admin() {
        this.setRole(Role.ADMIN);
    }
}
 
