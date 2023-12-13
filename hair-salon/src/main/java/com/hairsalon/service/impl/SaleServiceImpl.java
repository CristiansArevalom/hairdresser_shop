package com.hairsalon.service.impl;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Sale;
import com.hairsalon.repository.IGenericRepository;
//import com.hairsalon.repository.ISaleDetailRepository;
import com.hairsalon.repository.ISaleRepository;
import com.hairsalon.service.ISaleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleServiceImpl extends CRUDImp<Sale, Integer> implements ISaleService {

    private final ISaleRepository saleRepository;
    //private final ISaleDetailRepository saleDetailRepository;

    @Override
    protected IGenericRepository<Sale, Integer> getRepo() {
        return saleRepository;
    }

    @Override
    public Sale disable(Integer id) {
        Sale obj = saleRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return saleRepository.save(obj);
    }

    @Override
    public Sale saveSaleTransactional(Sale sale){
        Sale savedOrder = saleRepository.save(sale);
        /*
        savedOrder.getDetails().forEach(e -> {
            
            saleDetailRepository.save(e);

        });
        //1 guardar sale
        //2 guardar sale detail
        //3 guardar 
 */
        return savedOrder;
    }
}
