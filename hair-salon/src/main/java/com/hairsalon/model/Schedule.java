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
@Table(uniqueConstraints = { @UniqueConstraint(name = "CATEGORY_NAME_UK", columnNames = { "name"}) })
public class Schedule {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idSchedule;
    
    @Column(name="start_shift",nullable = false)
    private LocalDateTime startShift;
    
    @Column(name="end_shift",nullable = false)
    private LocalDateTime endShift;

    @Column(name="enabled",nullable = false)
    private boolean enabled;


}
