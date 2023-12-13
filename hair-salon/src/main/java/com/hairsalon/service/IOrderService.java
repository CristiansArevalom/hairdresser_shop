package com.hairsalon.service;


import com.hairsalon.model.Order;

public interface IOrderService extends ICRUD<Order,Integer>{
    
    Order saveTransactional(Order order);
}
