package com.hairsalon.dto;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO{   
    private Integer idEmployee;
    private String name;
    private String lastName;
    private String document;
    private char gender;
    private LocalDateTime birthDate;
    private String phone;
    private String address;
    private String email;
    private PositionDTO position;
    private boolean enabled;
}
