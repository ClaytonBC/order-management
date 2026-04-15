package com.clayton.ordermanagementapi.service;

import com.clayton.ordermanagementapi.dto.CreateOrderRequest;
import com.clayton.ordermanagementapi.dto.OrderItemRequest;
import com.clayton.ordermanagementapi.dto.OrderItemResponse;
import com.clayton.ordermanagementapi.dto.OrderResponse;
import com.clayton.ordermanagementapi.entity.Order;
import com.clayton.ordermanagementapi.entity.OrderItem;
import com.clayton.ordermanagementapi.entity.Product;
import com.clayton.ordermanagementapi.enums.Status;
import com.clayton.ordermanagementapi.repository.OrderRepository;
import com.clayton.ordermanagementapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Order updateStatus(Long orderId, Status newStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new RuntimeException("Invalid status transition");
        }

        order.setStatus(newStatus);

        return orderRepository.save(order);
    }

    public OrderResponse createOrder(CreateOrderRequest request) {

        Order order = new Order();
        order.setCustomer(request.getCustomer());
        order.setStatus(Status.RECEIVED);

        List<OrderItem> items = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(product.getPrice());
            item.setOrder(order);

            items.add(item);
        }

        order.setItems(items);

        order.setTotalPrice(order.calculateTotal());

        Order savedOrder = orderRepository.save(order);

        return toResponse(savedOrder);
    }

    private OrderResponse toResponse(Order order) {

        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getId(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getCustomer(),
                order.getTotalPrice(),
                items
        );
    }
}
