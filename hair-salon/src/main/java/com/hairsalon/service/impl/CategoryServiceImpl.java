package com.hairsalon.service.impl;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Category;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.ICategoryRepository;
import com.hairsalon.service.ICategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends CRUDImp<Category, Integer> implements ICategoryService {

    private final ICategoryRepository repository;

    @Override
    protected IGenericRepository<Category, Integer> getRepo() {
        return repository;
    }

    @Override
    public Category disable(Integer id) {
        Category obj = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return repository.save(obj);
    }

}
