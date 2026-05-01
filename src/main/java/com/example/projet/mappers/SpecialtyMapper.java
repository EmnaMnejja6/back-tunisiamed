package com.example.projet.mappers;

import com.example.projet.dto.SpecialtyDTO;
import com.example.projet.entities.Specialty;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyMapper {

    public SpecialtyDTO toDTO(Specialty specialty) {
        if (specialty == null) return null;
        SpecialtyDTO dto = new SpecialtyDTO();
        dto.setId(specialty.getId());
        dto.setLabel(specialty.getLabel());
        dto.setDescription(specialty.getDescription());
        dto.setIconUrl(specialty.getIconUrl());
        return dto;
    }

    public Specialty toEntity(SpecialtyDTO dto) {
        if (dto == null) return null;
        Specialty specialty = new Specialty();
        specialty.setLabel(dto.getLabel());
        specialty.setDescription(dto.getDescription());
        specialty.setIconUrl(dto.getIconUrl());
        return specialty;
    }
}
