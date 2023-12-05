package com.hairsalon.service;

import java.util.List;

import com.hairsalon.model.Appointment;
import com.hairsalon.model.Treatment;

public interface IAppointmentService extends ICRUD<Appointment,Integer>{

   Appointment saveTransactional(Appointment appointment,List<Treatment> treatments);

    
}
