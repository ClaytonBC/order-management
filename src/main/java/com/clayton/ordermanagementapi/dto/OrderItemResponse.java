package com.clayton.ordermanagementapi.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemResponse {

    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
