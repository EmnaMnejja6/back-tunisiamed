package com.example.projet.dto;

import java.time.LocalDateTime;

import com.example.projet.entities.enums.AppointmentStatus;

import lombok.Data;

@Data
public class AppointmentDTO {
    private Long id;
    private LocalDateTime appointmentDate;
    private AppointmentStatus status;
    private LocalDateTime createdAt;
    private Long quoteId;
    private String patientName;
    private String clinicName;
}
