package com.clayton.ordermanagementapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal price;
}
