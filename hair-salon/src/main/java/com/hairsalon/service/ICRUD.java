package com.hairsalon.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICRUD <T,ID> {

    T save(T t) throws Exception;

    T update(T t, ID id) throws Exception;

    List<T> readAll() throws Exception;

    T readById(ID id) throws Exception;

    void delete(ID id) throws Exception;

    void disable(ID id) throws Exception;

    Page<T> getPage(Pageable pageable) throws Exception;
}

