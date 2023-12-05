package com.hairsalon.service;

import java.util.List;

import com.hairsalon.model.Position;
import com.hairsalon.model.Schedule;

public interface IPositionService extends ICRUD<Position,Integer>{

    Position saveTransactional(Position position, List<Schedule>schedules,String day);
   


    
}
