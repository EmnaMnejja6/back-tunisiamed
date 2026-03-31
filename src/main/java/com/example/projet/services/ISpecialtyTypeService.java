package com.example.projet.services;

import com.example.projet.entities.SpecialtyType;
import java.util.List;

public interface ISpecialtyTypeService {
    public List<SpecialtyType> getAllSpecialtyTypes();
    public void addSpecialtyType(SpecialtyType specialtyType);
}
