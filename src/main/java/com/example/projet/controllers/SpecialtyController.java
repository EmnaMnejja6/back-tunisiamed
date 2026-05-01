package com.example.projet.controllers;

import com.example.projet.dto.SpecialtyDTO;
import com.example.projet.entities.Specialty;
import com.example.projet.mappers.SpecialtyMapper;
import com.example.projet.services.ISpecialtyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {

    @Autowired
    private ISpecialtyService specialtyService;

    @Autowired
    private SpecialtyMapper specialtyMapper;

    // GET /api/specialties
    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> getAll() {
        List<SpecialtyDTO> dtos = specialtyService.getSpecialties().stream()
                .map(specialtyMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET /api/specialties/{id}
    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(specialtyMapper.toDTO(specialtyService.getSpecialtyById(id)));
    }

    // POST /api/specialties
    @PostMapping
    public ResponseEntity<SpecialtyDTO> create(@RequestBody SpecialtyDTO dto) {
        Specialty specialty = specialtyMapper.toEntity(dto);
        Specialty saved = specialtyService.create(specialty);
        return ResponseEntity.status(HttpStatus.CREATED).body(specialtyMapper.toDTO(saved));
    }

    // PUT /api/specialties/{id}
    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> update(
            @PathVariable Long id,
            @RequestBody SpecialtyDTO dto
    ) {
        Specialty specialty = specialtyMapper.toEntity(dto);
        return ResponseEntity.ok(specialtyMapper.toDTO(specialtyService.update(id, specialty)));
    }

    // DELETE /api/specialties/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
