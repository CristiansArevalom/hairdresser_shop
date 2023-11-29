package com.hairsalon.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO extends RepresentationModel<PositionDTO>{
    private Integer idPosition;
    private String name;
    private Double salary;
    private boolean enabled;
}
