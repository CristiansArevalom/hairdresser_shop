package com.hairsalon.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO{   
    private Integer idClient;
    private String name;
    private String lastName;
    private String document;
    private char gender;
    private String phone;
    private String address;
    private String email;
    private boolean enabled;
}
