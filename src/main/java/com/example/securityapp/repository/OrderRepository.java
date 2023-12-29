package com.example.securityapp.repository;

import com.example.securityapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Optional<Order> findByOrderId(Long orderId);

    @Query(value = "select order from Order order where order.nameSender in ?1")
    List<Order> findOrderByUserNameIn(Set<String> nameSenders);
}
