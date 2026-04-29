package com.clayton.ordermanagementapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank
        @Schema(example = "Notebook Gamer")
        String name,

        @NotBlank
        @Schema(example = "High performance gaming notebook")
        String description,

        @NotNull
        @Schema(example = "4500.00")
        BigDecimal price

) { }
