package com.hairsalon.service.impl;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Sale;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.ISaleRepository;
import com.hairsalon.service.ISaleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl extends CRUDImp<Sale, Integer> implements ISaleService {

    private final ISaleRepository repository;

    @Override
    protected IGenericRepository<Sale, Integer> getRepo() {
        return repository;
    }

    @Override
    public Sale disable(Integer id) {
        Sale obj = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return repository.save(obj);
    }

}
