package com.hairsalon.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Inventory {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer barcode;

    @Column(name="expiration_date",nullable = false)
    private LocalDateTime expirationDate;
    
    @Column(name="entry_date",nullable = false)
    private LocalDateTime entryDate;

    @Column(name="state",nullable = false,length = 30)
    private String state;

    @Column(name="purchase_price",nullable = false,columnDefinition = "decimal(10,2)")
    private Double purchasePrice;

    @Column(name="selling_price",nullable = false,columnDefinition = "decimal(10,2)")
    private Double sellingPrice;

    
    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false, foreignKey = @ForeignKey(name="INV_ID_PRODUCT_FK"))
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_shelving", nullable = false, foreignKey = @ForeignKey(name="INV_ID_SHELVING_FK"))
    private Shelving shelving;
      
    @Column(name="enabled",nullable = false)
    private boolean enabled;


    @OneToOne()
    @JoinColumn(name="id_order_detail",nullable = false)
    private OrderDetail orderDetail;

}   


