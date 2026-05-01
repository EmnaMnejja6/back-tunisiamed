package com.example.projet.controllers;

import com.example.projet.dto.LoginRequest;
import com.example.projet.dto.LoginResponse;
import com.example.projet.entities.User;
import com.example.projet.repositories.UserRepository;
import com.example.projet.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.example.projet.dto.CreateUserRequest;
import com.example.projet.entities.Admin;
import com.example.projet.entities.ClinicAdmin;
import com.example.projet.mappers.UserMapper;
import com.example.projet.services.IUserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMapper;

    /**
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtTokenProvider.generateToken(
                    user.getEmail(),
                    user.getRole().name()
            );
            return ResponseEntity.ok(new LoginResponse(
                    token,
                    user.getRole().name(),
                    user.getId(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName()
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }

    /**
     * POST /api/auth/register/admin
     * Body: { "firstName", "lastName", "email", "password", "phone" }
     */
    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody CreateUserRequest request) {
        try {
            Admin admin = userMapper.toAdminEntity(request);
            Admin saved = userService.createAdmin(admin);
            // Auto-login → return token directly
            String token = jwtTokenProvider.generateToken(saved.getEmail(), saved.getRole().name());
            return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(
                    token,
                    saved.getRole().name(),
                    saved.getId(),
                    saved.getEmail(),
                    saved.getFirstName(),
                    saved.getLastName()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * POST /api/auth/register/clinic-admin
     * Body: { "firstName", "lastName", "email", "password", "phone" }
     */
    @PostMapping("/register/clinic-admin")
    public ResponseEntity<?> registerClinicAdmin(@RequestBody CreateUserRequest request) {
        try {
            ClinicAdmin clinicAdmin = userMapper.toClinicAdminEntity(request);
            ClinicAdmin saved = userService.createClinicAdmin(clinicAdmin);
            String token = jwtTokenProvider.generateToken(saved.getEmail(), saved.getRole().name());
            return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(
                    token,
                    saved.getRole().name(),
                    saved.getId(),
                    saved.getEmail(),
                    saved.getFirstName(),
                    saved.getLastName()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * GET /api/auth/me
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String email = jwtTokenProvider.getUsernameFromToken(token);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return ResponseEntity.ok(new LoginResponse(
                    token, user.getRole().name(), user.getId(),
                    user.getEmail(), user.getFirstName(), user.getLastName()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}