package com.hairsalon.model;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Appointment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idAppointment;

    @Column(name="date_appointment",nullable = false,length = 100)
    private LocalDateTime dateAppointment;

    @Column(name="total_price",nullable = false,columnDefinition = "decimal(20,2)")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "id_employee", nullable = false, foreignKey = @ForeignKey(name="APPOINTMENT_ID_EMPLOYEE_FK"))
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false, foreignKey = @ForeignKey(name="APPOINTMENT_ID_CLIENT_FK"))
    private Client client;    

    @Column(name="enabled",nullable = false)
    private boolean enabled;
    
}
