package com.clayton.ordermanagementapi.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    @NotBlank
    private String customer;

    @NotEmpty
    private List<OrderItemRequest> items;
}
