package com.clayton.ordermanagementapi.dto;

import com.clayton.ordermanagementapi.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {
    private String customer;
    private Status status;
}
