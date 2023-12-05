package com.hairsalon.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Sale {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idSale;

    @Column(name="date_appointment",nullable = false,length = 100)
    private LocalDateTime dateSale;

    @Column(name="total_price",nullable = false,columnDefinition = "decimal(20,2)")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "id_employee", nullable = false, foreignKey = @ForeignKey(name="SALE_ID_EMPLOYEE_FK"))
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false, foreignKey = @ForeignKey(name="SALE_ID_CLIENT_FK"))
    private Client client;    

    @Column(name="enabled",nullable = false)
    private boolean enabled;
    
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL) //, fetch = FetchType.LAZY)
    private List<SaleDetail> details;
}
