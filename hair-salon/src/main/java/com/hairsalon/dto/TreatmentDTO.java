package com.hairsalon.dto;


import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDTO{   
    private Integer idTreatment;
    private String name;
    private String description;
    private Double price;
    @Min(value = 1, message = "The duration should be at least 1 minute")
    @Max(value = 59, message = "The duration should be a maximum of 480 minutes")
    private Integer durationInMinutes;
    private boolean enabled;
}
