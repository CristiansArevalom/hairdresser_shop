package com.hairsalon.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SupplierDTO{   
    private Integer idSupplier;
    private String name;
    private String address;
     private String phone;
    private boolean enabled;
}
