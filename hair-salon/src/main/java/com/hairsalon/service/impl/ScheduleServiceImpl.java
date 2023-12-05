package com.hairsalon.service.impl;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Schedule;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.IScheduleRepository;
import com.hairsalon.service.IScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl extends CRUDImp<Schedule, Integer> implements IScheduleService {

    private final IScheduleRepository repository;

    @Override
    protected IGenericRepository<Schedule, Integer> getRepo() {
        return repository;
    }

    @Override
    public Schedule disable(Integer id) {
        Schedule obj = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return repository.save(obj);
    }

}
