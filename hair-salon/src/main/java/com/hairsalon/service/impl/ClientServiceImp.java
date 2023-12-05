package com.hairsalon.service.impl;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Client;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.IClientRepository;
import com.hairsalon.service.IClientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImp extends CRUDImp<Client, Integer> implements IClientService {

    private final IClientRepository repository;

    @Override
    protected IGenericRepository<Client, Integer> getRepo() {
        return repository;
    }

    @Override
    public Client disable(Integer id) {
        Client obj = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return repository.save(obj);
    }

}
