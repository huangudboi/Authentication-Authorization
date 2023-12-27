package com.example.securityapp.service;

import com.example.securityapp.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAll();
    Order save(Order order);
    void deleteOrderById(long orderId);
    Optional<Order> findByOrderId(Long orderId);

}
