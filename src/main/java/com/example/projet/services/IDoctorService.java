package com.example.projet.services;

import com.example.projet.entities.Doctor;
import java.util.List;

public interface IDoctorService {
    public Doctor create(Doctor doctor, Long clinicId, Long specialtyId);
    public Doctor update(Long id, Doctor doctor);
    public void delete(Long id);
    public Doctor getDoctorById(Long id);
    public List<Doctor> getDoctorsByClinic(Long clinicId);
    public List<Doctor> getDoctorsBySpecialty(Long specialtyId);
    public List<Doctor> getDoctorsByClinicAndSpecialty(Long clinicId, Long specialtyId);
}
