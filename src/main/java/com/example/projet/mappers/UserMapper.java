package com.example.projet.mappers;

import com.example.projet.dto.CreateUserRequest;
import com.example.projet.dto.UserDTO;
import com.example.projet.entities.Admin;
import com.example.projet.entities.ClinicAdmin;
import com.example.projet.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public Admin toAdminEntity(CreateUserRequest request) {
        if (request == null) return null;
        Admin admin = new Admin();
        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setEmail(request.getEmail());
        admin.setPasswordHash(request.getPassword());
        admin.setPhone(request.getPhone());
        return admin;
    }

    public ClinicAdmin toClinicAdminEntity(CreateUserRequest request) {
        if (request == null) return null;
        ClinicAdmin clinicAdmin = new ClinicAdmin();
        clinicAdmin.setFirstName(request.getFirstName());
        clinicAdmin.setLastName(request.getLastName());
        clinicAdmin.setEmail(request.getEmail());
        clinicAdmin.setPasswordHash(request.getPassword());
        clinicAdmin.setPhone(request.getPhone());
        return clinicAdmin;
    }
}
