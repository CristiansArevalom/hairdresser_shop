package com.hairsalon.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

//TODO corregir controller de Appointments
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentTreatmentDTO{

    private Integer idAppointmentDetail;

    private TreatmentDTO treatment;

    @JsonBackReference
    private AppointmentDTO appointment;
   
    private boolean enabled;

}
