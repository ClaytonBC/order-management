package com.clayton.ordermanagementapi.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(

        @NotNull
        @Schema(example = "1")
        Long productId,

        @NotNull
        @Min(1)
        @Schema(example = "2")
        Integer quantity

) { }
