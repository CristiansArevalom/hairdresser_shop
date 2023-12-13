package com.hairsalon.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.yaml.snakeyaml.events.Event.ID;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.service.ICRUD;


/* Es clase abstracta para poder declarar el metodo abstracto que obtenga la instancia del repository */
public abstract class CRUDImp<T,ID> implements ICRUD<T,ID>{

    protected abstract IGenericRepository<T,ID> getRepo();



    @Override
    public T save(T t){
       return this.getRepo().save(t);

    }

    @Override
    public T update(T t, ID id) throws Exception {
        
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND " + id));
        return getRepo().save(t);
    }

    @Override
    public List<T> readAll(){
        return getRepo().findAll();
    }

    @Override
    public T readById(ID id){
        return getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND " + id));
    }

    @Override
    public void delete(ID id){
        getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND: " + id));
        getRepo().deleteById(id);
    }

    @Override
    public Page<T> getPage(Pageable pageable){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPage'");
    }

    
}
