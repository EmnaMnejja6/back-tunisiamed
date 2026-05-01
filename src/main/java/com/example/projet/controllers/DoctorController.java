package com.example.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projet.entities.Doctor;
import com.example.projet.services.IDoctorService;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.projet.dto.CreateDoctorRequest;
import com.example.projet.dto.DoctorDTO;
import com.example.projet.mappers.DoctorMapper;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private DoctorMapper doctorMapper;

    // GET /api/doctors?clinicId=1&specialtyId=2
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAll(
            @RequestParam(required = false) Long clinicId,
            @RequestParam(required = false) Long specialtyId
    ) {
        List<Doctor> doctors;
        if (clinicId != null && specialtyId != null)
            doctors = doctorService.getDoctorsByClinicAndSpecialty(clinicId, specialtyId);
        else if (clinicId != null)
            doctors = doctorService.getDoctorsByClinic(clinicId);
        else if (specialtyId != null)
            doctors = doctorService.getDoctorsBySpecialty(specialtyId);
        else
            return ResponseEntity.badRequest().build();

        List<DoctorDTO> dtos = doctors.stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET /api/doctors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorMapper.toDTO(doctorService.getDoctorById(id)));
    }

    // POST /api/doctors?clinicId=1&specialtyId=2
    @PostMapping
    public ResponseEntity<DoctorDTO> create(@RequestBody CreateDoctorRequest request) {
        Doctor doctor = doctorMapper.toEntity(request);
        Doctor saved = doctorService.create(doctor, request.getClinicId(), request.getSpecialtyId());
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorMapper.toDTO(saved));
    }


    // PUT /api/doctors/{id}
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> update(@PathVariable Long id, @RequestBody CreateDoctorRequest request) {
        Doctor doctor = doctorMapper.toEntity(request);
        return ResponseEntity.ok(doctorMapper.toDTO(doctorService.update(id, doctor)));
    }

    // DELETE /api/doctors/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
