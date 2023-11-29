package com.hairsalon.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idPosition;

    @Column(name="name",nullable = false,length = 20)
    private String name;
    
    @Column(name="salary",nullable = false,columnDefinition = "decimal(20,2)")
    private Double salary;

    @Column(name="enabled",nullable = false)
    private boolean enabled;
}
