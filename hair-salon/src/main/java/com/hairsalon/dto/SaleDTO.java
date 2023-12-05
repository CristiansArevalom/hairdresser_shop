package com.hairsalon.dto;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO{

    private Integer idSale;
    private LocalDateTime dateSale;
    private Double totalPrice;
    private EmployeeDTO employee;
    private ClientDTO client;    
    private boolean enabled;

    @JsonManagedReference
    @NotNull
    private List<SaleDetailDTO> details;
}
