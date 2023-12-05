package com.hairsalon.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO{   
    private Integer idCategory;
    private String name;
    private String description;
    private boolean enabled;
}
