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

public class OrderDetail {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idOrderDetail;

    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false, foreignKey = @ForeignKey(name="ORDERDETAIL_ID_ORDER_FK"))
    private Order order;
 
    @Column(name="enabled",nullable = false)
    private boolean enabled;
    
    @OneToOne(mappedBy = "orderDetail", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private Inventory inventory;
}
