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
public class Shelving {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idShelving;

    @Column(name="description",nullable = false,length = 500)
    private String description;

    @Column(name="enabled",nullable = false)
    private boolean enabled;


}
