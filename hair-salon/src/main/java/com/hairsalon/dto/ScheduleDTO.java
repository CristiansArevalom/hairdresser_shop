package com.hairsalon.dto;



import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO{   
    private Integer idSchedule;
    private LocalDateTime startShift;
    private LocalDateTime endShift;
    private boolean enabled;
}
