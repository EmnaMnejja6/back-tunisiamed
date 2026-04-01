package com.example.projet.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ClinicSpecialtyDTO {
    private Long id;
    private String specialtyTypeName;
    private BigDecimal basePriceEstimate;
    private Integer recommendedMinDays;
    private List<DoctorDTO> doctors;


}
