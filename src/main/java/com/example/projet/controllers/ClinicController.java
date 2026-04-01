package com.example.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projet.dto.ClinicDTO;
import com.example.projet.entities.Clinic;
import com.example.projet.entities.ClinicSpecialty;
import com.example.projet.entities.Doctor;
import com.example.projet.entities.Review;
import com.example.projet.services.IClinicService;
import java.util.List;
import com.example.projet.mappers.ClinicMapper;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    @Autowired
    private IClinicService clinicService;

    @Autowired
    private ClinicMapper clinicMapper;

    // GET /api/clinics?city=Tunis&specialtyId=1
    @GetMapping
    //@PreAuthorize("hasRole('PUBLIC')")
    public ResponseEntity<List<ClinicDTO>> getAllClinics(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Long specialtyId) {
        List<Clinic> clinics = clinicService.getAllClinics(city, specialtyId);
        List<ClinicDTO> dtos = clinics.stream()
                .map(clinicMapper::toDTO)
                .collect(Collectors.toList());        
        return ResponseEntity.ok(dtos);
    }

    // GET /api/clinics/{id}
    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('PUBLIC')")
    public ResponseEntity<ClinicDTO> getClinicById(@PathVariable Long id) {
        //return ResponseEntity.ok(clinicService.getClinicById(id));
        return ResponseEntity.ok(clinicMapper.toDTO(clinicService.getClinicById(id)));
    }

    // GET /api/clinics/{id}/doctors
    @GetMapping("/{id}/doctors")
    //@PreAuthorize("hasRole('PUBLIC')")
    public ResponseEntity<List<Doctor>> getDoctorsByClinic(@PathVariable Long id) {
        return ResponseEntity.ok(clinicService.getDoctorsByClinicId(id));
    }

    // GET /api/clinics/{id}/doctors/{doctorId}
    @GetMapping("/{id}/doctors/{doctorId}")
    //@PreAuthorize("hasRole('PUBLIC')")
    public ResponseEntity<Doctor> getDoctorById(
            @PathVariable Long id,
            @PathVariable Long doctorId) {
        return ResponseEntity.ok(clinicService.getDoctorById(id, doctorId));
    }

    // GET /api/clinics/{id}/reviews
    @GetMapping("/{id}/reviews")
    //@PreAuthorize("hasRole('PUBLIC')")
    public ResponseEntity<List<Review>> getReviewsByClinic(@PathVariable Long id) {
        return ResponseEntity.ok(clinicService.getReviewsByClinicId(id));
    }

    // GET /api/clinics/{id}/specialties
    @GetMapping("/{id}/specialties")
    //@PreAuthorize("hasRole('PUBLIC')")
    public ResponseEntity<List<ClinicSpecialty>> getSpecialtiesByClinic(@PathVariable Long id) {
        return ResponseEntity.ok(clinicService.getSpecialtiesByClinicId(id));
    }

    // POST /api/clinics — ADMIN
    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addClinic(@RequestBody Clinic clinic) {
        clinicService.addClinic(clinic);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // PUT /api/clinics/{id} — ADMIN / CLINIC_ADMIN
    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> updateClinic(
            @PathVariable Long id,
            @RequestBody Clinic clinic) {       
        clinicService.updateClinic(id, clinic);
        return ResponseEntity.ok().build();
    }

    // DELETE /api/clinics/{id} — ADMIN
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        clinicService.deleteClinic(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/clinics/{id}/doctors — CLINIC_ADMIN
    @PostMapping("/{id}/doctors")
    //@PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> addDoctor(
            @PathVariable Long id,
            @RequestBody Doctor doctor) {
        clinicService.addDoctor(id, doctor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // PUT /api/clinics/{id}/doctors/{doctorId} — CLINIC_ADMIN
    @PutMapping("/{id}/doctors/{doctorId}")
    //@PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> updateDoctor(
            @PathVariable Long id,
            @PathVariable Long doctorId,
            @RequestBody Doctor doctor) {
        clinicService.updateDoctor(id, doctorId, doctor);
        return ResponseEntity.ok().build();
    }

    // DELETE /api/clinics/{id}/doctors/{doctorId} — CLINIC_ADMIN
    @DeleteMapping("/{id}/doctors/{doctorId}")
    //@PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> deleteDoctor(
            @PathVariable Long id,
            @PathVariable Long doctorId) {
        clinicService.deleteDoctor(id, doctorId);
        return ResponseEntity.noContent().build();
    }

    // POST /api/clinics/{id}/specialties — CLINIC_ADMIN
    @PostMapping("/{id}/specialties")
    //@PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> addSpecialty(
            @PathVariable Long id,
            @RequestParam Long specialtyTypeId,
            @RequestBody ClinicSpecialty clinicSpecialty) {
        clinicService.addSpecialtyToClinic(id, specialtyTypeId, clinicSpecialty);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // PUT /api/clinics/{id}/specialties/{csId} — CLINIC_ADMIN
    @PutMapping("/{id}/specialties/{csId}")
    //@PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> updateSpecialty(
            @PathVariable Long id,
            @PathVariable Long csId,
            @RequestBody ClinicSpecialty clinicSpecialty) {
        clinicService.updateClinicSpecialty(id, csId, clinicSpecialty);
        return ResponseEntity.ok().build();
    }

    // DELETE /api/clinics/{id}/specialties/{csId} — CLINIC_ADMIN
    @DeleteMapping("/{id}/specialties/{csId}")
    //@PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> removeSpecialty(
            @PathVariable Long id,
            @PathVariable Long csId) {
        clinicService.removeSpecialtyFromClinic(id, csId);
        return ResponseEntity.noContent().build();
    }
    
}
