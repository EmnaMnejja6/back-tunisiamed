package com.example.projet.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projet.entities.Appointment;
import com.example.projet.entities.Quote;
import com.example.projet.entities.QuoteStatus;
import com.example.projet.entities.enums.AppointmentStatus;
import com.example.projet.repositories.AppointmentRepository;
import com.example.projet.repositories.QuoteRepository;
import java.util.List;


@Service
public class AppointmentServiceImpl implements IAppointmentService {
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public void scheduleAppointment(Long quoteId) {
        Quote quote = quoteRepository.findById(quoteId).orElseThrow(() -> new RuntimeException("Quote not found"));
        if (quote.getStatus() != QuoteStatus.APPROVED) 
            throw new RuntimeException("Quote must be APPROVED to create an appointment");
        if (quote.getAppointment() != null) 
            throw new RuntimeException("Appointment already exists for this quote");

        Appointment appointment = new Appointment();
        appointment.setQuote(quote);
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointment.setAppointmentDate(LocalDateTime.now().plusDays(quote.getDurationDays()));

        appointmentRepository.save(appointment);
    }

    public List<Appointment> getMyAppointments(Long patientId) {
        return appointmentRepository.findByQuotePatientId(patientId);
    }
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
    }
    public List<Appointment> getAppointmentByClinic(Long clinicId) {
        return appointmentRepository.findByQuoteClinicId(clinicId);
    }

    public void completeAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
        if (appointment.getStatus() != AppointmentStatus.CONFIRMED)
            throw new RuntimeException("Only CONFIRMED appointments can be completed");
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
    }
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new RuntimeException("Appointment not found"));
        if (appointment.getStatus() == AppointmentStatus.COMPLETED)
            throw new RuntimeException("Cannot cancel a COMPLETED appointment");
        appointment.setStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);
    }
}