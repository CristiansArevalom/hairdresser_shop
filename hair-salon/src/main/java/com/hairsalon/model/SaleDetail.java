package com.hairsalon.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

public class SaleDetail {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idSaleDetail;

    @Column(name="selling_price",nullable = false,columnDefinition = "decimal(20,2)")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "id_sale", nullable = false, foreignKey = @ForeignKey(name="SALE_ID_SALE_FK"))
    private Sale sale;    

    @Column(name="enabled",nullable = false)
    private boolean enabled;
        
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="barcode")
    private Inventory inventory;
    
}
