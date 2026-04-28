package com.clayton.ordermanagementapi.controller;

import com.clayton.ordermanagementapi.dto.CreateOrderRequest;
import com.clayton.ordermanagementapi.dto.OrderResponse;
import com.clayton.ordermanagementapi.dto.UpdateOrderStatusRequest;
import com.clayton.ordermanagementapi.entity.Order;
import com.clayton.ordermanagementapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request) {

        Order updatedOrder = orderService.updateStatus(id, request.status());

        OrderResponse response = new OrderResponse(
                updatedOrder.getId(),
                updatedOrder.getStatus(),
                updatedOrder.getCustomer(),
                updatedOrder.getTotalPrice(),
                List.of()
        );

        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody CreateOrderRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        OrderResponse response = orderService.createOrder(request, email);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll(
            Authentication authentication
    ) {

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(role ->
                        role.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return ResponseEntity.ok(orderService.findAllOrders());
        }

        String email = authentication.getName();

        return ResponseEntity.ok(orderService.findAll(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }
}
