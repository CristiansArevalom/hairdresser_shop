package com.hairsalon.dto;


import java.util.List;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentListTreatmentDTO{   
    @NotNull
    private AppointmentDTO appointment;
    @NotNull
    private List<TreatmentDTO> lstTreatments;
}
