package com.hairsalon.service.impl;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Treatment;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.ITreatmentRepository;
import com.hairsalon.service.ITreatmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TreatmentServiceImp extends CRUDImp<Treatment, Integer> implements ITreatmentService {

    private final ITreatmentRepository repository;

    @Override
    protected IGenericRepository<Treatment, Integer> getRepo() {
        return repository;
    }

    @Override
    public Treatment disable(Integer id) {
        Treatment obj = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return repository.save(obj);
    }

}
