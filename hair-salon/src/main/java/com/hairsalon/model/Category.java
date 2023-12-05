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
@Table(uniqueConstraints = { @UniqueConstraint(name = "CATEGORY_NAME_UK", columnNames = { "name"}) })
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idCategory;

    @Column(name="name",nullable = false,length = 100,unique = true)
    private String name;

    @Column(name="description",nullable = false,length = 200)
    private String description;

    @Column(name="enabled",nullable = false)
    private boolean enabled;


}
