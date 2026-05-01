package com.example.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projet.entities.Clinic;
import com.example.projet.services.IClinicService;
import java.util.List;

import com.example.projet.dto.ClinicDTO;
import com.example.projet.dto.CreateClinicRequest;
import com.example.projet.mappers.ClinicMapper;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    @Autowired
    private IClinicService clinicService;
    @Autowired
    private ClinicMapper clinicMapper;

    // GET /api/clinics
    @GetMapping
    public ResponseEntity<List<ClinicDTO>> getAllClinics(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Long specialtyId,
            @RequestParam(required = false) String keyword
    ) {
        List<Clinic> clinics;
        if (city != null)            clinics = clinicService.getClinicByCity(city);
        else if (specialtyId != null) clinics = clinicService.getClinicBySpecialty(specialtyId);
        else if (keyword != null)     clinics = clinicService.searchByKeyword(keyword);
        else                          clinics = clinicService.getAllClinics();
 
        List<ClinicDTO> dtos = clinics.stream()
                .map(clinicMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET /api/clinics/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ClinicDTO> getClinicById(@PathVariable Long id) {
        return ResponseEntity.ok(clinicMapper.toDTO(clinicService.getClinicById(id)));
    }

    // GET /api/clinics/admin/{adminId}
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<ClinicDTO>> getByAdmin(@PathVariable Long adminId) {
        List<ClinicDTO> dtos = clinicService.getClinicByAdminId(adminId).stream()
                .map(clinicMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // POST /api/clinics?clinicAdminId=1
    @PostMapping
    public ResponseEntity<ClinicDTO> create(@RequestBody CreateClinicRequest request) {
        Clinic clinic = clinicMapper.toEntity(request);
        Clinic saved = clinicService.createClinic(clinic, request.getClinicAdminId());
        return ResponseEntity.status(HttpStatus.CREATED).body(clinicMapper.toDTO(saved));
    }

    // PUT /api/clinics/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ClinicDTO> update(
            @PathVariable Long id,
            @RequestBody CreateClinicRequest request
    ) {
        Clinic clinic = clinicMapper.toEntity(request);
        return ResponseEntity.ok(clinicMapper.toDTO(clinicService.updateClinic(id, clinic)));
    }

    // DELETE /api/clinics/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clinicService.deleteClinic(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/clinics/{clinicId}/specialties/{specialtyId}
    @PreAuthorize("hasRole('CLINIC_ADMIN')") 
    @PostMapping("/{clinicId}/specialties/{specialtyId}")
    public ResponseEntity<ClinicDTO> addSpecialty(
            @PathVariable Long clinicId,
            @PathVariable Long specialtyId
    ) {
        return ResponseEntity.ok(clinicMapper.toDTO(clinicService.addSpecialty(clinicId, specialtyId)));
    }

    // DELETE /api/clinics/{clinicId}/specialties/{specialtyId}
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    @DeleteMapping("/{clinicId}/specialties/{specialtyId}")
    public ResponseEntity<Void> removeSpecialty(
            @PathVariable Long clinicId,
            @PathVariable Long specialtyId
    ) {
        clinicService.removeSpecialty(clinicId, specialtyId);
        return ResponseEntity.noContent().build();
    }


    
}
