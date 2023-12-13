package com.hairsalon.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hairsalon.model.AppointmentTreatment;

public interface IAppointmentTreatmentRepository extends IGenericRepository<AppointmentTreatment,Integer>{
    @Modifying
    @Query(value = "INSERT INTO appointment_treatment(id_appointment,id_treatment,enabled) VALUES (:idAppointment,:idTreatment,:enabled)",nativeQuery = true)
    Integer saveTreatment(@Param("idAppointment") Integer idAppointment,@Param("idTreatment") Integer idTreatment,@Param("enabled") boolean enabled);
    
}
