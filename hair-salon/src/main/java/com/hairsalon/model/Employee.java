package com.hairsalon.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.Check;

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
@Table(uniqueConstraints = { 
    @UniqueConstraint(name = "EMP_DOCUMENT_UK", columnNames = "document"),
    @UniqueConstraint(name = "EMP_EMAIL_UK", columnNames = "email"),
    @UniqueConstraint(name = "EMP_GENDER_UK", columnNames = "gender")})

public class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idEmployee;

    @Column(name="name",nullable = false,length = 100)
    private String name;

    @Column(name="last_name",nullable = false,length = 100)
    private String lastName;

    @Column(name="document",nullable = false,length = 8, unique = true)
    private String document;

    @Column(name = "gender", length = 1, nullable = false)
    @Check(constraints = "GENDER IN ('M','F')")
    private char gender;

    @Column(name="birth_date",nullable = false)
    private LocalDateTime birthDate;

    @Column(nullable = false,length = 9)
    private String phone;

    @Column(nullable = false,length = 150)
    private String address;

    @Column(nullable = false,length = 55, unique = true)
    private String email;

    @Column(name="enabled",nullable = false)
    private boolean enabled;
    
    @ManyToOne
    @JoinColumn(name = "id_position", nullable = false, foreignKey = @ForeignKey(name="EMP_ID_POSITION_FK"))
    private Position position;



}
