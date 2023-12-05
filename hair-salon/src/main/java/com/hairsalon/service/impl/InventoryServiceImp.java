package com.hairsalon.service.impl;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Inventory;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.IInventoryRepository;
import com.hairsalon.service.IInventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImp extends CRUDImp<Inventory, Integer> implements IInventoryService {

    private final IInventoryRepository repository;

    @Override
    protected IGenericRepository<Inventory, Integer> getRepo() {
        return repository;
    }

    @Override
    public Inventory disable(Integer id) {
        Inventory obj = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return repository.save(obj);
    }

}
