package com.clayton.ordermanagementapi.dto;
import com.clayton.ordermanagementapi.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


public record UpdateOrderStatusRequest(

        @NotNull
        @Schema(example = "PAID")
        Status status
) { }
