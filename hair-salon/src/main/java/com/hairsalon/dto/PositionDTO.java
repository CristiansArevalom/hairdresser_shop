package com.hairsalon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {
    private Integer idPosition;
    private String name;
    private Double salary;
    private boolean enabled;
}
