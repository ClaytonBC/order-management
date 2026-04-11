package com.clayton.ordermanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
public class ProductResponse {

    private  Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean available;
}
