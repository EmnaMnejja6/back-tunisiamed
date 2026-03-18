package com.example.projet.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table (uniqueConstraints=@UniqueConstraint(columnNames = {"clinic_id", "specialty_type_id"}))
public class ClinicSpecialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Clinic clinic;
    @ManyToOne
    private SpecialtyType specialtyType;
    private BigDecimal basePriceEstimate;
    private Integer recommendedMinDays;
    @OneToMany(mappedBy="clinicSpecialty")
    private List<Doctor> doctors;
}
