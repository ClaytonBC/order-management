package com.clayton.ordermanagementapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotNull
        BigDecimal price

) { }
