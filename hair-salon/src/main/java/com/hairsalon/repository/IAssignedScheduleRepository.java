package com.hairsalon.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hairsalon.model.AssignedSchedule;


public interface IAssignedScheduleRepository extends IGenericRepository<AssignedSchedule,Integer>{
    
    @Modifying
    @Query(value="INSERT INTO assigned_schedule(day,enabled,id_schedule,id_position) VALUES (:day,:enabled,:idSchedule,:idPosition)",nativeQuery=true)
    Integer saveSchedule(@Param("day")String day,@Param("enabled")boolean enabled,@Param("idSchedule")Integer idSchedule,@Param("idPosition")Integer idPosition);
    
}
