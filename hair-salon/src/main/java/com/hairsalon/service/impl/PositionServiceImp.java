package com.hairsalon.service.impl;
import org.springframework.stereotype.Service;


import com.hairsalon.model.Position;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.IPositionRepository;
import com.hairsalon.service.IPositionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PositionServiceImp extends CRUDImp<Position,Integer> implements IPositionService{
    
    private final IPositionRepository repository;

    @Override
    protected IGenericRepository<Position,Integer> getRepo() {
        return repository;
    }
    



}
