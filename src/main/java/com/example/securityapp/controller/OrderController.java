package com.example.securityapp.controller;

import com.example.securityapp.model.Message;
import com.example.securityapp.model.Order;
import com.example.securityapp.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getAllOrder")
    public ResponseEntity<List<Order>> findAllOrders() {
        List<Order> orders = orderService.findAll();
        ResponseEntity<List<Order>> response;
        try {
            response = new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (IndexOutOfBoundsException ex) {
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PostMapping(value = "/createOrder")
    public ResponseEntity<Order> createOrder(@RequestBody @Valid Order order) {
        ResponseEntity<Order> response;
        try {
            Order oderSave = orderService.createOrder(order);
            response = new ResponseEntity<>(oderSave, HttpStatus.CREATED);
        } catch (Exception ex) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @DeleteMapping(value = "/deleteOrder/{order_id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("order_id") Long orderId) {
        ResponseEntity<String> response;
        try {
            orderService.deleteOrderById(orderId);
            response = new ResponseEntity<>("Delete order successful", HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping(value = "/detailOrder/{order_id}")
    public ResponseEntity<Order> getDetailOrder(@PathVariable("order_id") Long orderId) {
        ResponseEntity<Order> response;
        try {
            Order order = orderService.findByOrderId(orderId);
            response = new ResponseEntity<>(order, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}