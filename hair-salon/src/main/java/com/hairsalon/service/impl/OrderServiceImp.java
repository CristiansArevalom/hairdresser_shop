package com.hairsalon.service.impl;


import org.springframework.stereotype.Service;

import com.hairsalon.exception.ModelNotFoundException;
import com.hairsalon.model.Inventory;
import com.hairsalon.model.Order;
import com.hairsalon.model.OrderDetail;
import com.hairsalon.repository.IGenericRepository;
import com.hairsalon.repository.IInventoryRepository;
import com.hairsalon.repository.IOrderDetailRepository;
import com.hairsalon.repository.IOrderRepository;
import com.hairsalon.service.IOrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImp extends CRUDImp<Order, Integer> implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IInventoryRepository invRepository;
    private final IOrderDetailRepository orderDetailRepository;

    @Override
    protected IGenericRepository<Order, Integer> getRepo() {
        return orderRepository;
    }

            

    @Override
    public Order disable(Integer id) {
        Order obj = orderRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        obj.setEnabled(false);
        return orderRepository.save(obj);
    }


    @Transactional
    @Override
    public Order saveTransactional(Order order) {
             order.setEnabled(true);
             order.getDetails().forEach(detail -> detail.setEnabled(true));
             Order savedOrder = orderRepository.save(order);
             for (OrderDetail detail : order.getDetails()) {
                 detail.setOrder(savedOrder);
                 OrderDetail savedDetail = orderDetailRepository.save(detail);
                 Inventory inventory = savedDetail.getInventory();
                 inventory.setOrderDetail(savedDetail);
                 invRepository.save(inventory);
             }
             return savedOrder;
         }
          
     }



    
