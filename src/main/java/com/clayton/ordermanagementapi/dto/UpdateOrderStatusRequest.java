package com.clayton.ordermanagementapi.dto;
import com.clayton.ordermanagementapi.enums.Status;
import jakarta.validation.constraints.NotNull;


public record UpdateOrderStatusRequest(

        @NotNull
        Status status
) { }
