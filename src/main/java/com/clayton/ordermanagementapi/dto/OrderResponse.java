package com.clayton.ordermanagementapi.dto;
import com.clayton.ordermanagementapi.enums.Status;
import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(

        Long id,
        Status status,
        String customer,
        BigDecimal totalPrice,
        List<OrderItemResponse> items
) {}