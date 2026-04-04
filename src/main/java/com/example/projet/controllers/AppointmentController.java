package com.example.projet.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.projet.entities.Appointment;
import com.example.projet.services.IAppointmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

import com.example.projet.dto.AppointmentDTO;
import com.example.projet.mappers.AppointmentMapper;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private AppointmentMapper appointmentMapper;

    // POST /api/appointments — PATIENT
    @PostMapping
    //@PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Void> scheduleAppointment(@RequestParam Long quoteId) {
        appointmentService.scheduleAppointment(quoteId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // GET /api/appointments/my — PATIENT
    @GetMapping("/my")
    //@PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<AppointmentDTO>> getMyAppointments(@RequestParam Long patientId) {
        return ResponseEntity.ok(appointmentService.getMyAppointments(patientId).stream()
            .map(appointmentMapper::toDTO)
            .collect(Collectors.toList()));
    }

    // GET /api/appointments/clinic — CLINIC_ADMIN
    @GetMapping("/clinic")
    //@PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByClinic(@RequestParam Long clinicId) {
        return ResponseEntity.ok(appointmentService.getAppointmentByClinic(clinicId).stream()
            .map(appointmentMapper::toDTO)
            .collect(Collectors.toList()));
    }

    // GET /api/appointments/{id} — PATIENT / CLINIC_ADMIN
    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('PATIENT') or hasRole('CLINIC_ADMIN')")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentMapper.toDTO(appointmentService.getAppointmentById(id)));
    }

    // PATCH /api/appointments/{id}/complete — CLINIC_ADMIN
    @PatchMapping("/{id}/complete")
    //@PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> completeAppointment(@PathVariable Long id) {
        appointmentService.completeAppointment(id);
        return ResponseEntity.ok().build();
    }

    // PATCH /api/appointments/{id}/cancel — PATIENT / CLINIC_ADMIN
    @PatchMapping("/{id}/cancel")
    //@PreAuthorize("hasRole('PATIENT') or hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok().build();
    }
}
