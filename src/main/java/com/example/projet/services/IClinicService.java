package com.example.projet.services;
import com.example.projet.entities.Clinic;
import com.example.projet.entities.ClinicSpecialty;
import com.example.projet.entities.Doctor;
import com.example.projet.entities.Review;

import java.util.List;

public interface IClinicService {
    public List<Clinic> getAllClinics(String city, Long specialtyId);
    public Clinic getClinicById(Long id);
    public List<Doctor> getDoctorsByClinicId(Long clinicId);
    public List<Review> getReviewsByClinicId(Long clinicId);
    public List<ClinicSpecialty> getSpecialtiesByClinicId(Long clinicId);
    public void addClinic(Clinic clinic);
    public void updateClinic(Clinic clinic);
    public void deleteClinic(Long id);

}
