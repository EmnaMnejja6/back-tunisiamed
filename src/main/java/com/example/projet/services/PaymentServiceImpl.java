package com.example.projet.services;

import com.example.projet.repositories.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projet.entities.Appointment;
import com.example.projet.entities.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.example.projet.repositories.AppointmentRepository;
import com.example.projet.entities.enums.AppointmentStatus;
import com.example.projet.entities.enums.PaymentStatus;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;
    
    public Payment simulatePayment(Long appointmentId) {
       Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED)
            throw new RuntimeException("Appointment must be CONFIRMED");

        if (appointment.getPayment() != null)
            throw new RuntimeException("Payment already exists for this appointment");

        //BigDecimal total = appointment.getQuote().getFinalApprovedCost();
        BigDecimal commissionRate = new BigDecimal("0.07");
        //BigDecimal commissionAmount = total.multiply(commissionRate);
        //BigDecimal clinicAmount = total.subtract(commissionAmount);

        Payment payment = new Payment();
        payment.setAppointment(appointment);
        //payment.setAmountTotal(total);
        payment.setCommissionRate(commissionRate);
        //payment.setCommissionAmount(commissionAmount);
        //payment.setClinicAmount(clinicAmount);
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);

    }
    public List<Payment> getMyPayments(Long patientId) {
        return paymentRepository.findByAppointmentQuotePatientId(patientId);
    }
    public List<Payment> getClinicRevenues(Long clinicId) {
        return paymentRepository.findByAppointmentQuoteClinicId(clinicId);
    }
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
