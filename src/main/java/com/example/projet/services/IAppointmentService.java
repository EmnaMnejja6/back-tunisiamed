package com.example.projet.services;
import com.example.projet.entities.Appointment;

import java.util.List;

public interface IAppointmentService {
    public void scheduleAppointment(Long quoteId);
    public List<Appointment> getMyAppointments(Long patientId);
    public Appointment getAppointmentById(Long id);
    public List<Appointment> getAppointmentByClinic(Long clinicId);
    public void completeAppointment(Long id);
    public void cancelAppointment(Long appointmentId);
}
