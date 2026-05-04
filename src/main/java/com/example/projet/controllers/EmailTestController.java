package com.example.projet.controllers;

import com.example.projet.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class EmailTestController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<Map<String, String>> testEmail(@RequestParam String email) {
        Map<String, String> response = new HashMap<>();
        
        try {
            System.out.println("=== TEST EMAIL ENDPOINT CALLED ===");
            System.out.println("Email: " + email);
            
            emailService.sendQuoteRequestConfirmation(
                email,
                "Test",
                "User",
                "test-token-123"
            );
            
            response.put("status", "success");
            response.put("message", "Email sending initiated. Check logs for details.");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
