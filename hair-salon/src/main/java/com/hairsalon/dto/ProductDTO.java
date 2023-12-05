package com.hairsalon.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO{   
    private Integer idProduct;
    private String name;
    private String description;
    private String photoUrl;
    private boolean enabled;
    private CategoryDTO category;    

}
