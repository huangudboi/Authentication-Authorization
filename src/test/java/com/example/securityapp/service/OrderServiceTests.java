package com.example.securityapp.service;

import com.example.securityapp.Dto.response.PokemonResponse;
import com.example.securityapp.model.Order;
import com.example.securityapp.model.Pokemon;
import com.example.securityapp.repository.OrderRepository;
import com.example.securityapp.service.impl.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    public void init() {
        order = Order.builder().nameSender("Pham Van Huan").phoneSender("0981729501").addressSender("Hanoi").emailSender("dsadsadsadsad@gmail.com")
                .nameReceiver("Nguyen Van A").phoneReceiver("0345353578").addressReceiver("Hcm").emailReceiver("ttttttt@gmail.com")
                .latitude(33).latitude(45)
                .build();
    }

    @Test
    public void OrderService_FindAllOrder() {

        List<Order> orderList = Mockito.mock(List.class);

        when(orderRepository.findAll()).thenReturn(orderList);

        List<Order> saveOrder = orderService.findAll();

        Assertions.assertThat(saveOrder).isNotNull();
    }

    @Test
    public void OrderService_CreateOrder_ReturnOrder() {
        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        Order savedOrder = orderService.createOrder(order);

        Assertions.assertThat(savedOrder).isNotNull();
    }

    @Test
    public void OrderService_DeleteOrderById_ReturnVoid() {
        long orderId = 1L;

        when(orderRepository.findByOrderId(orderId)).thenReturn(Optional.ofNullable(order));

        doNothing().when(orderRepository).delete(order);

        assertAll(() -> orderService.deleteOrderById(orderId));
    }

    @Test
    public void OrderService_FindOrderById_ReturnOrder() {
        long orderId = 1L;

        when(orderRepository.findByOrderId(orderId)).thenReturn(Optional.ofNullable(order));

        Order orderReturn = orderService.findByOrderId(orderId);

        Assertions.assertThat(orderReturn).isNotNull();
    }
}
