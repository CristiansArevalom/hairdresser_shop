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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="order_data")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idOrder;

    @Column(name="date_order",nullable = false)
    private LocalDateTime dateOrder;

    @ManyToOne
    @JoinColumn(name = "id_supplier", nullable = false, foreignKey = @ForeignKey(name="ORDER_ID_SUPPLIER_FK"))
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "id_employee", nullable = false, foreignKey = @ForeignKey(name="ORDER_ID_EMPLOYEE_FK"))
    private Employee employee;    

    @Column(name="enabled",nullable = false)
    private boolean enabled;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //, fetch = FetchType.LAZY)
    private List<OrderDetail> details;
}
