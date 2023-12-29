package com.example.securityapp.service;

import com.example.securityapp.Dto.response.OrderResponse;
import com.example.securityapp.model.Order;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrderService {

    List<Order> findAll();
    Order createOrder(Order order);
    void deleteOrderById(long orderId);
    Order findByOrderId(Long orderId);
    OrderResponse importAndValidateExcel(MultipartFile multipartFile);
}
