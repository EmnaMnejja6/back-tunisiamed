package com.example.projet.services;
import com.example.projet.entities.Clinic;


import java.util.List;

public interface IClinicService {
    public Clinic createClinic(Clinic clinic, Long clinicAdminId);
    public Clinic updateClinic(Long id, Clinic clinic);
    public void deleteClinic(Long id);
    public Clinic getClinicById(Long id);
    public List<Clinic> getAllClinics();
    public List<Clinic> getClinicByCity(String city);
    public List<Clinic> getClinicBySpecialty(Long specialtyId);
    public List<Clinic> searchByKeyword(String keyword);
    public List<Clinic> getClinicByAdminId(Long adminId);
    public Clinic addSpecialty(Long clinicId, Long specialtyId);
    public void removeSpecialty(Long clinicId, Long specialtyId);
    public void updateRating(Long clinicId);
}
