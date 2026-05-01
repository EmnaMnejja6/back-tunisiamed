package com.example.projet.services;

import com.example.projet.entities.Admin;
import com.example.projet.entities.ClinicAdmin;
import com.example.projet.entities.User;
import com.example.projet.repositories.AdminRepository;
import com.example.projet.repositories.ClinicAdminRepository;
import com.example.projet.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.projet.entities.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired 
    private AdminRepository adminRepository;
    @Autowired
    private ClinicAdminRepository clinicAdminRepository;
    @Autowired
     private PasswordEncoder passwordEncoder;
    
    public Admin createAdmin(Admin admin) {
        if (userRepository.existsByEmail(admin.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + admin.getEmail());
        }
        admin.setRole(Role.ADMIN);
        admin.setPasswordHash(passwordEncoder.encode(admin.getPasswordHash()));
        return adminRepository.save(admin);
    }

    public ClinicAdmin createClinicAdmin(ClinicAdmin clinicAdmin) {
        if (userRepository.existsByEmail(clinicAdmin.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + clinicAdmin.getEmail());
        }
        clinicAdmin.setRole(Role.CLINIC_ADMIN);
        clinicAdmin.setPasswordHash(passwordEncoder.encode(clinicAdmin.getPasswordHash()));
        return clinicAdminRepository.save(clinicAdmin);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User update(Long id, User updatedUser) {
        User existing = getUserById(id);
        existing.setFirstName(updatedUser.getFirstName());
        existing.setLastName(updatedUser.getLastName());
        existing.setPhone(updatedUser.getPhone());
        // Email change requires uniqueness check
        if (!existing.getEmail().equals(updatedUser.getEmail())) {
            if (userRepository.existsByEmail(updatedUser.getEmail())) {
                throw new IllegalArgumentException("Email already in use: " + updatedUser.getEmail());
            }
            existing.setEmail(updatedUser.getEmail());
        }
        return userRepository.save(existing);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
