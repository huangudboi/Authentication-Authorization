package com.example.securityapp.service.impl;

import com.example.securityapp.exceptions.OrderNotFoundException;
import com.example.securityapp.exceptions.PokemonNotFoundException;
import com.example.securityapp.model.Order;
import com.example.securityapp.model.Pokemon;
import com.example.securityapp.repository.OrderRepository;
import com.example.securityapp.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order order) {
       return orderRepository.save(order);
    }

    @Override
    public void deleteOrderById(long orderId) {
        Order order = orderRepository.findByOrderId(orderId).orElseThrow(() -> new OrderNotFoundException("OrderId could not be found"));
        orderRepository.delete(order);
    }

    @Override
    public Optional<Order> findByOrderId(Long orderId) {
        return orderRepository.findByOrderId(orderId);
    }
}
