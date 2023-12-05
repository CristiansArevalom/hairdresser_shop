package com.hairsalon.service;

import java.util.List;

import com.hairsalon.model.Inventory;
import com.hairsalon.model.Order;
import com.hairsalon.model.OrderDetail;

public interface IOrderService extends ICRUD<Order,Integer>{
    
    Order saveTransactional(Order order,List<Inventory> inventory);
}
