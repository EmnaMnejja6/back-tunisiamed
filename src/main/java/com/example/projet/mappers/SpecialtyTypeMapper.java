package com.example.projet.mappers;

import com.example.projet.entities.SpecialtyType;
import com.example.projet.dto.SpecialtyTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyTypeMapper {
    public SpecialtyTypeDTO toDTO(SpecialtyType st) {
        SpecialtyTypeDTO dto = new SpecialtyTypeDTO();
        dto.setId(st.getId());
        dto.setName(st.getName());
        dto.setDescription(st.getDescription());
        dto.setIconUrl(st.getIconUrl());
        return dto;
    }
}
