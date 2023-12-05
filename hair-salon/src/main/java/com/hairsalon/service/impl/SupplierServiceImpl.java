package com.hairsalon.service.impl;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Supplier;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.ISupplierRepository;
import com.hairsalon.service.ISupplierService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl extends CRUDImp<Supplier, Integer> implements ISupplierService {

    private final ISupplierRepository repository;

    @Override
    protected IGenericRepository<Supplier, Integer> getRepo() {
        return repository;
    }

    @Override
    public Supplier disable(Integer id) {
        Supplier obj = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return repository.save(obj);
    }

}
