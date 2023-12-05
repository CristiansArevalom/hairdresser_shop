package com.hairsalon.service.impl;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Shelving;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.IShelvingRepository;
import com.hairsalon.service.IShelvingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShelvingServiceImpl extends CRUDImp<Shelving, Integer> implements IShelvingService {

    private final IShelvingRepository repository;

    @Override
    protected IGenericRepository<Shelving, Integer> getRepo() {
        return repository;
    }

    @Override
    public Shelving disable(Integer id) {
        Shelving obj = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return repository.save(obj);
    }

}
