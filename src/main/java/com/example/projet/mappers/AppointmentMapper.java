package com.example.projet.mappers;

import org.springframework.stereotype.Component;

import com.example.projet.dto.AppointmentDTO;
import com.example.projet.entities.Appointment;

@Component
public class AppointmentMapper {
    public AppointmentDTO toDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setStatus(appointment.getStatus());
        dto.setCreatedAt(appointment.getCreatedAt());
        dto.setQuoteId(appointment.getQuote().getId());
        dto.setPatientName(appointment.getQuote().getPatient().getFirstName()
            + " " + appointment.getQuote().getPatient().getLastName());
        dto.setClinicName(appointment.getQuote().getClinic().getName());
        return dto;
    }
}
