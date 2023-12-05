package com.hairsalon.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionScheduleDTO{   
    private PositionDTO position;
    private List<ScheduleDTO> lstSchedules;
    private String day;
}
