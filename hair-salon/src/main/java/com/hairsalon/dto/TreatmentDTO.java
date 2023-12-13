package com.hairsalon.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDTO{

    private Integer idTreatment;

    @NotNull
    @NotEmpty
    @Size(min=3, max=100,message = "The name should be between 3 and 100 characters")
    private String name;

    @NotNull
    @NotEmpty
    @Size(min=3, max=500,message = "The description should be between 3 and 100 characters")
    private String description;

    @Min(value = 1,message = "The price should be at least 1")
    private Double price;

    @Min(value = 1, message = "The duration should be at least 1 minute")
    //@Max(value = 59, message = "The duration should be a maximum of 480 minutes")
    private Integer durationInMinutes;

    @NotNull
    private boolean enabled;
}
