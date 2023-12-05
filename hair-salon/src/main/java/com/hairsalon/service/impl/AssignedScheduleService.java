package com.hairsalon.service.impl;
import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.AssignedSchedule;
import com.hairsalon.repository.IAssignedScheduleRepository;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.service.IAssignedScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignedScheduleService extends CRUDImp<AssignedSchedule,Integer> implements IAssignedScheduleService{
    
    private final IAssignedScheduleRepository repository;

    @Override
    protected IGenericRepository<AssignedSchedule,Integer> getRepo() {
        return repository;
    }

    @Override
    public AssignedSchedule disable(Integer id) {
        AssignedSchedule  obj = repository.findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return repository.save(obj);
    }
    



}
