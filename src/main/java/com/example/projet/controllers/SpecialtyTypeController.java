package com.example.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.projet.entities.SpecialtyType;
import com.example.projet.services.ISpecialtyTypeService;
import java.util.List;

@RestController
@RequestMapping("/api/specialty-types")
public class SpecialtyTypeController {

    @Autowired
    private ISpecialtyTypeService specialtyTypeService;

    // GET /api/specialty-types — public
    @GetMapping
    //@PreAuthorize("hasRole('PUBLIC')")
    public ResponseEntity<List<SpecialtyType>> getAllSpecialtyTypes() {
        return ResponseEntity.ok(specialtyTypeService.getAllSpecialtyTypes());
    }

    // POST /api/specialty-types — ADMIN
    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addSpecialtyType(@RequestBody SpecialtyType specialtyType) {
        specialtyTypeService.addSpecialtyType(specialtyType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
