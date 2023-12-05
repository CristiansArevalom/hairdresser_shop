package com.hairsalon.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDetailDTO{

    private Integer idSaleDetail;
    private Double totalPrice;
    private InventoryDTO inventory;
    private boolean enabled;
    
    @JsonBackReference
    private SaleDTO sale;

}
