package com.hairsalon.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints = { @UniqueConstraint(name = "PRODUCT_NAME_UK", columnNames = { "name"}) })
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idProduct;

    @Column(name="name",nullable = false,length = 100,unique = true)
    private String name;

    @Column(name="description",nullable = false,length = 500)
    private String description;

    @Column(name="enabled",nullable = false)
    private boolean enabled;

    private String photoUrl;

    
    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false, foreignKey = @ForeignKey(name="PRODUCT_ID_CATEGORY_FK"))
    private Category category;    

}
