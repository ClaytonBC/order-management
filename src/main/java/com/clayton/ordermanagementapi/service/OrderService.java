package com.clayton.ordermanagementapi.service;

import com.clayton.ordermanagementapi.dto.CreateOrderRequest;
import com.clayton.ordermanagementapi.entity.Order;
import com.clayton.ordermanagementapi.enums.Status;
import com.clayton.ordermanagementapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order updateStatus(Long orderId, Status newStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new RuntimeException("Invalid status transition");
        }

        order.setStatus(newStatus);

        return orderRepository.save(order);
    }

    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();

        order.setCustomer(request.getCustomer());
        order.setStatus(request.getStatus());

        return orderRepository.save(order);
    }
}
