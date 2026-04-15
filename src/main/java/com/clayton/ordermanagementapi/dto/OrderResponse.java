package com.clayton.ordermanagementapi.dto;
import com.clayton.ordermanagementapi.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private Status status;
    private String customer;
    private BigDecimal totalPrice;
    private List<OrderItemResponse> items;
}
