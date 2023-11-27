package com.carevalom.hairdresser.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Service {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer id_service;

    @Column(name="name",nullable = false)
    String name;

    @Column(name="description",nullable = false)
    String description;

    @Column(name="price",nullable = false)
    String price;
    
    @Column(name="duration",nullable = false)
    LocalDateTime duration;    
}
