package com.clayton.ordermanagementapi.repository;

import com.clayton.ordermanagementapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer(String customer);
}
