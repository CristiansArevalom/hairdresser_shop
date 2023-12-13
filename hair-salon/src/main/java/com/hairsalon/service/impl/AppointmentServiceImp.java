package com.hairsalon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Appointment;
import com.hairsalon.model.Treatment;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.IAppointmentRepository;
import com.hairsalon.repository.IAppointmentTreatmentRepository;
import com.hairsalon.service.IAppointmentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImp extends CRUDImp<Appointment, Integer> implements IAppointmentService {

    private final IAppointmentRepository appointmentRepository;
    private final IAppointmentTreatmentRepository appointmentTreatmentRepository;

    @Override
    protected IGenericRepository<Appointment, Integer> getRepo() {
        return appointmentRepository;
    }

    @Override
    public Appointment disable(Integer id) {
        Appointment obj = appointmentRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return appointmentRepository.save(obj);
    }


    @Transactional
    @Override
    public Appointment saveTransactional(Appointment appointment, List<Treatment> treatments){
        appointmentRepository.save(appointment);//Guardando maestro detalle
        treatments.forEach(treatment ->{
        appointmentTreatmentRepository.saveTreatment(appointment.getIdAppointment(),treatment.getIdTreatment(),true);
        });
        return appointment;
    }

}
