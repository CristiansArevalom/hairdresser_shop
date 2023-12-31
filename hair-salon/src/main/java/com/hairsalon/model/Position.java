package com.hairsalon.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints = { @UniqueConstraint(name = "POSITION_NAME_UK", columnNames = { "name"}) })
public class Position {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idPosition;

    @Column(name="name",nullable = false,length = 20,unique = true)
    private String name;
    
    @Column(name="salary",nullable = false,columnDefinition = "decimal(20,2)")
    private Double salary;

    @Column(name="enabled",nullable = false)
    private boolean enabled;
}
