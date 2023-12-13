package com.hairsalon.dto;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class OrderDTO{   
    private Integer idOrder;
    private LocalDateTime dateOrder;
    private SupplierDTO supplier;
    private EmployeeDTO employee;
    private boolean enabled;
    
    @JsonManagedReference
    @NotNull
    private List<OrderDetailDTO> details;

}
