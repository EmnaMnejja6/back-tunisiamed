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
    public void updateClinic(Long id, Clinic clinic);
    public void deleteClinic(Long id);

    public Doctor getDoctorById(Long clinicId, Long id);
    public void addDoctor(Long clinicId, Doctor doctor);
    public void updateDoctor(Long clinicId,Long doctorId, Doctor doctor);
    public void deleteDoctor(Long clinicId, Long id);

    public void addSpecialtyToClinic(Long clinicId, Long specialtyTypeId, ClinicSpecialty clinicSpecialty);
    public void updateClinicSpecialty(Long clinicId, Long csId, ClinicSpecialty clinicSpecialty);
    public void removeSpecialtyFromClinic(Long clinicId, Long csId);
}
