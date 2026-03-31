package com.example.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.projet.entities.Payment;
import com.example.projet.services.IPaymentService;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    // POST /api/payments/simulate/{appointmentId} — PATIENT
    @PostMapping("/simulate/{appointmentId}")
    //@PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Payment> simulatePayment(@PathVariable Long appointmentId) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(paymentService.simulatePayment(appointmentId));
    }

    // GET /api/payments/my — PATIENT
    @GetMapping("/my")
    //@PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<Payment>> getMyPayments(@RequestParam Long patientId) {
        return ResponseEntity.ok(paymentService.getMyPayments(patientId));
    }

    // GET /api/payments/clinic — CLINIC_ADMIN
    @GetMapping("/clinic")
    //@PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<List<Payment>> getClinicRevenues(@RequestParam Long clinicId) {
        return ResponseEntity.ok(paymentService.getClinicRevenues(clinicId));
    }

    // GET /api/payments/admin/all — ADMIN
    @GetMapping("/admin/all")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
}
