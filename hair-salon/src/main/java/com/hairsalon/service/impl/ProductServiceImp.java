package com.hairsalon.service.impl;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Product;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.IProductRepository;
import com.hairsalon.service.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImp extends CRUDImp<Product, Integer> implements IProductService {

    private final IProductRepository repository;

    @Override
    protected IGenericRepository<Product, Integer> getRepo() {
        return repository;
    }

    @Override
    public Product disable(Integer id) {
        Product obj = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return repository.save(obj);
    }

}
