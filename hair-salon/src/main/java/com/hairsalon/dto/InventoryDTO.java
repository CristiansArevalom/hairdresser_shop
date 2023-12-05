package com.hairsalon.dto;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO{   
    private Integer barcode;
    private LocalDateTime expirationDate;
    private LocalDateTime entryDate;
    private String state;
    private Double purchasePrice;
    private Double sellingPrice;
    private ProductDTO product;
    private ShelvingDTO shelving;

    @JsonBackReference
    private OrderDetailDTO orderDetail;
    private boolean enabled;
    
}
