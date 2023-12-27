package com.example.securityapp.repository;

import com.example.securityapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Optional<Order> findByOrderId(Long orderId);
}
