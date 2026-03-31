package com.example.projet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projet.entities.SpecialtyType;
import com.example.projet.repositories.SpecialtyTypeRepository;
import java.util.List;

@Service
public class SpecialtyTypeServiceImpl implements ISpecialtyTypeService {
    @Autowired
    private SpecialtyTypeRepository specialtyTypeRepository;

    public List<SpecialtyType> getAllSpecialtyTypes() {
        return specialtyTypeRepository.findAll();
    }

    public void addSpecialtyType(SpecialtyType specialtyType) {
        specialtyTypeRepository.save(specialtyType);
    }
}
