package com.clayton.ordermanagementapi.dto;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CreateOrderRequest(

        @NotEmpty
        List<OrderItemRequest> items
) { }
