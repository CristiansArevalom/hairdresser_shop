package com.hairsalon.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICRUD <T,ID> {

    T save(T t);

    T update(T t, ID id) throws Exception;

    List<T> readAll();

    T readById(ID id) ;

    void delete(ID id);

    T disable(ID id);

    Page<T> getPage(Pageable pageable);
}

