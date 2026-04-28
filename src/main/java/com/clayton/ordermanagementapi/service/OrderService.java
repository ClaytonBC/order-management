package com.clayton.ordermanagementapi.service;

import com.clayton.ordermanagementapi.dto.CreateOrderRequest;
import com.clayton.ordermanagementapi.dto.OrderItemRequest;
import com.clayton.ordermanagementapi.dto.OrderItemResponse;
import com.clayton.ordermanagementapi.dto.OrderResponse;
import com.clayton.ordermanagementapi.entity.Order;
import com.clayton.ordermanagementapi.entity.OrderItem;
import com.clayton.ordermanagementapi.entity.Product;
import com.clayton.ordermanagementapi.enums.Status;
import com.clayton.ordermanagementapi.exception.BusinessException;
import com.clayton.ordermanagementapi.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new BusinessException("Invalid status transition");
        }

        order.setStatus(newStatus);

        return orderRepository.save(order);
    }

    public OrderResponse createOrder(CreateOrderRequest request, String userEmail) {

        Order order = new Order();
        order.setCustomer(userEmail);
        order.setStatus(Status.RECEIVED);

        List<OrderItem> items = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.items()) {

            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemRequest.quantity());
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

    public List<OrderResponse> findAll(String email) {

        return orderRepository.findByCustomer(email)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return toResponse(order);
    }
    public List<OrderResponse> findAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
