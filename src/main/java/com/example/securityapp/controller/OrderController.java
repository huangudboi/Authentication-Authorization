package com.example.securityapp.controller;

import com.example.securityapp.Dto.response.OrderResponse;
import com.example.securityapp.model.Order;
import com.example.securityapp.service.OrderService;
import com.example.securityapp.utils.ExcelGenerator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/export-to-excel")
    public void exportIntoExcelFile(HttpServletResponse response){
        List <Order> listOfOrders = orderService.findAll();
        try {
            ExcelGenerator generator = new ExcelGenerator(listOfOrders);
            generator.generateExcelFile(response);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @PostMapping("/importExcel")
    public ResponseEntity<OrderResponse> importExcel(@RequestParam("file") MultipartFile file) {
        ResponseEntity<OrderResponse> response;
        try {
            // Call a service to handle Excel import and validation
            OrderResponse orderResponse = orderService.importAndValidateExcel(file);
            response = new ResponseEntity<>(orderResponse, HttpStatus.OK);

        } catch (RuntimeException e) {
            e.printStackTrace();
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}