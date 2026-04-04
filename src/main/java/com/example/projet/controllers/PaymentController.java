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

import com.example.projet.dto.PaymentDTO;
import com.example.projet.entities.Payment;
import com.example.projet.mappers.PaymentMapper;
import com.example.projet.services.IPaymentService;
import java.util.List;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private PaymentMapper paymentMapper;

    // POST /api/payments/simulate/{appointmentId} — PATIENT
    @PostMapping("/simulate/{appointmentId}")
    //@PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PaymentDTO> simulatePayment(@PathVariable Long appointmentId) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(paymentMapper.toDTO(paymentService.simulatePayment(appointmentId)));
    }

    // GET /api/payments/my — PATIENT
    @GetMapping("/my")
    //@PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<PaymentDTO>> getMyPayments(@RequestParam Long patientId) {
        return ResponseEntity.ok(paymentService.getMyPayments(patientId).stream()
            .map(paymentMapper::toDTO)
            .collect(Collectors.toList()));
    }

    // GET /api/payments/clinic — CLINIC_ADMIN
    @GetMapping("/clinic")
    //@PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<List<PaymentDTO>> getClinicRevenues(@RequestParam Long clinicId) {
        return ResponseEntity.ok(paymentService.getClinicRevenues(clinicId).stream()
            .map(paymentMapper::toDTO)
            .collect(Collectors.toList()));
    }

    // GET /api/payments/admin/all — ADMIN
    @GetMapping("/admin/all")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments().stream()
            .map(paymentMapper::toDTO)
            .collect(Collectors.toList()));
    }
}
