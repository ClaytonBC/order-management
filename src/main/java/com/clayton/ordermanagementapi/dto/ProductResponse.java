package com.clayton.ordermanagementapi.dto;
import java.math.BigDecimal;

public record ProductResponse(

        Long id,
        String name,
        String description,
        BigDecimal price,
        Boolean available

) { }
