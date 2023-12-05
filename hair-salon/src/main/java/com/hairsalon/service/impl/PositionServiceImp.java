package com.hairsalon.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Position;
import com.hairsalon.model.Schedule;
import com.hairsalon.repository.IAssignedScheduleRepository;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.IPositionRepository;
import com.hairsalon.service.IPositionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PositionServiceImp extends CRUDImp<Position,Integer> implements IPositionService{
    
    private final IPositionRepository positionRepository;
    private final IAssignedScheduleRepository assignedScheduleRepository;

    @Override
    protected IGenericRepository<Position,Integer> getRepo() {
        return positionRepository;
    }

    @Override
    public Position disable(Integer id) {
        Position  obj = positionRepository.findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return positionRepository.save(obj);
    }

    @Transactional
    @Override
    public Position saveTransactional(Position position, List<Schedule> schedules,String day) {
        positionRepository.save(position);
        schedules.forEach(schedule -> assignedScheduleRepository.saveSchedule(day.toUpperCase(), true, schedule.getIdSchedule(), position.getIdPosition()));
        return position;
    }
    



}
