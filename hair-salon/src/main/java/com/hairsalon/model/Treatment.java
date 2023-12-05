package com.hairsalon.model;

import java.time.LocalDateTime;

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
@Table(uniqueConstraints = { @UniqueConstraint(name = "TREATMENT_NAME_UK", columnNames = { "name"}) })

public class Treatment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idTreatment;

    @Column(name="name",nullable = false,length = 100,unique = true)
    private String name;

    @Column(name="description",nullable = false,length = 500)
    private String description;

    @Column(name="price",nullable = false,columnDefinition = "decimal(20,2)")
    private Double price;


    @Column(name="duration",nullable = false)
    private Integer durationInMinutes;

    @Column(name="enabled",nullable = false)
    private boolean enabled;


}
