package com.example.securityapp.repository;


import com.example.securityapp.model.Order;
import com.example.securityapp.model.Pokemon;
import com.example.securityapp.model.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void OrderRepository_SaveOrder_ReturnsSavedOrder() {
        Order order = Order.builder().nameSender("Pham Van Huan").phoneSender("0981729501").addressSender("Hanoi").emailSender("dsadsadsadsad@gmail.com")
                .nameReceiver("Nguyen Van A").phoneReceiver("0345353578").addressReceiver("Hcm").emailReceiver("ttttttt@gmail.com")
                .latitude(33).latitude(45)
                .build();

        Order savedOrder = orderRepository.save(order);

        Assertions.assertThat(savedOrder).isNotNull();
        Assertions.assertThat(savedOrder.getOrderId()).isGreaterThan(0);
    }

    @Test
    public void OrderRepository_GetAllOrder_ReturnsMoreThenOneReview() {
        Order order = Order.builder().nameSender("Pham Van Huan").phoneSender("0981729501").addressSender("Hanoi").emailSender("dsadsadsadsad@gmail.com")
                .nameReceiver("Nguyen Van A").phoneReceiver("0345353578").addressReceiver("Hcm").emailReceiver("ttttttt@gmail.com")
                .latitude(33).latitude(45)
                .build();
        Order order2 = Order.builder().nameSender("Pham Van Huan").phoneSender("0981729501").addressSender("Hanoi").emailSender("dsadsadsadsad@gmail.com")
                .nameReceiver("Nguyen Van A").phoneReceiver("0345353578").addressReceiver("Hcm").emailReceiver("ttttttt@gmail.com")
                .latitude(33).latitude(45)
                .build();

        orderRepository.save(order);
        orderRepository.save(order2);

        List<Order> orderList = orderRepository.findAll();

        Assertions.assertThat(orderList).isNotNull();
        Assertions.assertThat(orderList.size()).isEqualTo(2);
    }

    @Test
    public void OrderRepository_FindByOrderId_ReturnsSavedOrder() {
        Order order = Order.builder().nameSender("Pham Van Huan").phoneSender("0981729501").addressSender("Hanoi").emailSender("dsadsadsadsad@gmail.com")
                .nameReceiver("Nguyen Van A").phoneReceiver("0345353578").addressReceiver("Hcm").emailReceiver("ttttttt@gmail.com")
                .latitude(33).latitude(45)
                .build();

        orderRepository.save(order);

        Order reviewOrder = orderRepository.findByOrderId(order.getOrderId()).get();

        Assertions.assertThat(reviewOrder).isNotNull();
    }

    @Test
    public void OrderRepository_DeleteOrderById_ReturnVoid() {
        Order order = Order.builder().nameSender("Pham Van Huan").phoneSender("0981729501").addressSender("Hanoi").emailSender("dsadsadsadsad@gmail.com")
                .nameReceiver("Nguyen Van A").phoneReceiver("0345353578").addressReceiver("Hcm").emailReceiver("ttttttt@gmail.com")
                .latitude(33).latitude(45)
                .build();

        orderRepository.save(order);

        orderRepository.deleteById(order.getOrderId());
        Optional<Order> pokemonOrder = orderRepository.findByOrderId(order.getOrderId());

        Assertions.assertThat(pokemonOrder).isEmpty();
    }
}
