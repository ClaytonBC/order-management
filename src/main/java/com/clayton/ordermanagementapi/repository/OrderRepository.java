package com.clayton.ordermanagementapi.repository;

import com.clayton.ordermanagementapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
