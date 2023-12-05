package com.hairsalon.service.impl;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Employee;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.IEmployeeRepository;
import com.hairsalon.service.IEmployeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp extends CRUDImp<Employee, Integer> implements IEmployeeService {

    private final IEmployeeRepository repository;

    @Override
    protected IGenericRepository<Employee, Integer> getRepo() {
        return repository;
    }

    @Override
    public Employee disable(Integer id) {
        Employee obj = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return repository.save(obj);
    }

}
