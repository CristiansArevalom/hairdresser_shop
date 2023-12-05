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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="appointment_treatment")
public class AppointmentTreatment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idAppointmentDetail;

    @ManyToOne
    @JoinColumn(name = "id_treatment", nullable = false, foreignKey = @ForeignKey(name="APPOINTMENT_DET_ID_EMPLOYEE_FK"))
    private Treatment treatment;

    @ManyToOne
    @JoinColumn(name = "id_appointment", nullable = false, foreignKey = @ForeignKey(name="APPOINTMENT_DET_ID_APPOINTMENT_FK"))
    private Appointment appointment;    

    @Column(name="enabled",nullable = false)
    private boolean enabled;
    
}
