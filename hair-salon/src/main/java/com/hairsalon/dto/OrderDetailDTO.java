package com.hairsalon.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO{   

    private Integer idOrderDetail;
    @JsonBackReference
    private OrderDTO order;
    
    private ProductDTO product;
    private boolean enabled;

    private InventoryDTO inventory;
}
