package com.example.projet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projet.entities.Specialty;
import com.example.projet.repositories.SpecialtyRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class SpecialtyServiceImpl implements ISpecialtyService {
    
    @Autowired
    private SpecialtyRepository specialtyRepository;

    public Specialty create(Specialty specialty) {
        if (specialtyRepository.existsByLabel(specialty.getLabel())) {
            throw new IllegalArgumentException("Specialty already exists: " + specialty.getLabel());
        }
        return specialtyRepository.save(specialty);
    }

    public Specialty update(Long id, Specialty updated) {
        Specialty existing = getSpecialtyById(id);
        existing.setLabel(updated.getLabel());
        existing.setDescription(updated.getDescription());
        existing.setIconUrl(updated.getIconUrl());
        return specialtyRepository.save(existing);
    }

    public void delete(Long id) {
        if (!specialtyRepository.existsById(id)) {
            throw new EntityNotFoundException("Specialty not found with id: " + id);
        }
        specialtyRepository.deleteById(id);
    }

    public Specialty getSpecialtyById(Long id) {
        return specialtyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Specialty not found with id: " + id));
    }

    public List<Specialty> getSpecialties() {
        return specialtyRepository.findAll();
    }

}
