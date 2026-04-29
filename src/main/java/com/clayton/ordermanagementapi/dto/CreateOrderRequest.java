package com.clayton.ordermanagementapi.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CreateOrderRequest(

        @NotEmpty
        @Schema(description = "List of items in the order")
        List<OrderItemRequest> items
) { }
