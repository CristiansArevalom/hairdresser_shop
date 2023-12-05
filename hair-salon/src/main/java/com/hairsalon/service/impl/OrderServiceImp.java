package com.hairsalon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Inventory;
import com.hairsalon.model.Order;
import com.hairsalon.model.OrderDetail;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.IOrderRepository;
import com.hairsalon.service.IOrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImp extends CRUDImp<Order, Integer> implements IOrderService {

    private final IOrderRepository repository;

    @Override
    protected IGenericRepository<Order, Integer> getRepo() {
        return repository;
    }

            

    @Override
    public Order disable(Integer id) {
        Order obj = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return repository.save(obj);
    }



    @Override
    public Order saveTransactional(Order order, List<Inventory> inventory) {
        

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveTransactional'");
    }


  

}
