package com.hairsalon.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Supplier {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idSupplier;

    @Column(name="name",nullable = false,length = 100)
    private String name;

    @Column(name="address",nullable = false,length = 100)
    private String address;


    @Column(nullable = false,length = 9)
    private String phone;


    @Column(name="enabled",nullable = false)
    private boolean enabled;
    
}
