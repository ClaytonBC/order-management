package com.clayton.ordermanagementapi.controller;

import com.clayton.ordermanagementapi.dto.CreateOrderRequest;
import com.clayton.ordermanagementapi.dto.OrderResponse;
import com.clayton.ordermanagementapi.dto.UpdateOrderStatusRequest;
import com.clayton.ordermanagementapi.entity.Order;
import com.clayton.ordermanagementapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request) {

        Order updatedOrder = orderService.updateStatus(id, request.getStatus());

        OrderResponse response = new OrderResponse();
        response.setId(updatedOrder.getId());
        response.setStatus(updatedOrder.getStatus());

        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody CreateOrderRequest request) {

        OrderResponse response = orderService.createOrder((request));
        return ResponseEntity.ok(response);
    }
}
