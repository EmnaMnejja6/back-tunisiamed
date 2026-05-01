package com.example.projet.controllers;

import com.example.projet.entities.Admin;
import com.example.projet.entities.ClinicAdmin;
import com.example.projet.entities.User;
import com.example.projet.services.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.projet.dto.CreateUserRequest;
import com.example.projet.dto.UserDTO;
import com.example.projet.mappers.UserMapper;
import java.util.stream.Collectors;

import java.util.List;

import org.springframework.security.core.Authentication;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/debug")
    public ResponseEntity<?> debug(Authentication auth) {
        if (auth == null) return ResponseEntity.ok("No auth");
        return ResponseEntity.ok(Map.of(
            "email", auth.getName(),
            "authorities", auth.getAuthorities().toString()
        ));
    }

    // GET /api/users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> dtos = userService.getUsers().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toDTO(userService.getUserById(id)));
    }

    // GET /api/users/email/{email}
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userMapper.toDTO(userService.getUserByEmail(email)));
    }

    // POST /api/users/admins
    @PostMapping("/admins")
    public ResponseEntity<UserDTO> createAdmin(@RequestBody CreateUserRequest request) {
        Admin admin = userMapper.toAdminEntity(request);
        Admin saved = userService.createAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(saved));
    }

    // POST /api/users/clinic-admins
    @PostMapping("/clinic-admins")
    public ResponseEntity<UserDTO> createClinicAdmin(@RequestBody CreateUserRequest request) {
        ClinicAdmin clinicAdmin = userMapper.toClinicAdminEntity(request);
        ClinicAdmin saved = userService.createClinicAdmin(clinicAdmin);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(saved));
    }

    // PUT /api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable Long id,
            @RequestBody CreateUserRequest request
    ) {
        // Reuse toAdminEntity just to map base User fields (role not changed here)
        User user = userMapper.toAdminEntity(request);
        return ResponseEntity.ok(userMapper.toDTO(userService.update(id, user)));
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
