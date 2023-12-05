package com.hairsalon.model;

import org.hibernate.annotations.Check;

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
@Table(uniqueConstraints = { 
    @UniqueConstraint(name = "CLI_DOCUMENT_UK", columnNames = "document"),
    @UniqueConstraint(name = "CLI_EMAIL_UK", columnNames = "email"),
    })

public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idClient;

    @Column(name="name",nullable = false,length = 100)
    private String name;

    @Column(name="last_name",nullable = false,length = 100)
    private String lastName;

    @Column(name="document",nullable = false,length = 8, unique = true)
    private String document;

    @Column(name = "gender", length = 1, nullable = false)
    @Check(constraints = "GENDER IN ('M','F')")
    private char gender;

    @Column(nullable = false,length = 9)
    private String phone;

    @Column(nullable = false,length = 150)
    private String address;

    @Column(nullable = false,length = 55, unique = true)
    private String email;

    @Column(name="enabled",nullable = false)
    private boolean enabled;
    
}
