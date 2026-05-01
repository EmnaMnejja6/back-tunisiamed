package com.example.projet.services;

import com.example.projet.entities.Specialty;
import java.util.List;

public interface ISpecialtyService {
    public Specialty create(Specialty specialty);
    public Specialty update(Long id, Specialty specialty);
    public void delete(Long id);
    public Specialty getSpecialtyById(Long id);
    public List<Specialty> getSpecialties();
}
