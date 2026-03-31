package com.example.projet.services;
import com.example.projet.entities.Payment;
import java.util.List;

public interface IPaymentService {
    public Payment simulatePayment(Long appointmentId);
    public List<Payment> getMyPayments(Long patientId);
    public List<Payment> getClinicRevenues(Long clinicId);
    public List<Payment> getAllPayments();
}
