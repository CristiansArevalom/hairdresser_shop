package com.hairsalon.dto;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO{

    private Integer idAppointment;

    @NotNull
    @NotEmpty
    private LocalDateTime dateAppointment;

    @NotNull
    private Double totalPrice;

    @NotNull
    @NotEmpty
    private EmployeeDTO employee;

    @NotNull
    @NotEmpty
    private ClientDTO client;    

    @NotNull
    private boolean enabled;
}
