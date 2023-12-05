package com.hairsalon.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelvingDTO{   
    private Integer idShelving;
    private String description;
    private boolean enabled;
}
