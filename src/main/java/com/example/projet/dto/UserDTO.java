package com.example.projet.dto;

import java.time.LocalDateTime;
import com.example.projet.entities.enums.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Role role;
    private LocalDateTime createdAt;
}