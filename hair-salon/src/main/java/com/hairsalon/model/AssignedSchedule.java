package com.hairsalon.model;

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
@Table(name="assigned_schedule", uniqueConstraints = { @UniqueConstraint(name = "CATEGORY_NAME_UK", columnNames = { "name"}) })
public class AssignedSchedule {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idAssignedSchedule;
    

    @Column(name="day",nullable = false)
    /**No siempre funciona con todos los motores de base de datos */
    @Check(constraints = "DAY IN ('MONDAY,'THUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY')")
    private String day;

    @ManyToOne
    @JoinColumn(name = "id_position", nullable = false, foreignKey = @ForeignKey(name="ASSIG_SCHED_ID_POSITION_FK"))
    private Position position;

    @ManyToOne
    @JoinColumn(name = "id_schedule", nullable = false, foreignKey = @ForeignKey(name="ASSIG_SCHED_ID_SCHEDULE_FK"))
    private Schedule idSchedule;

    @Column(name="enabled",nullable = false)
    private boolean enabled;

    

}
