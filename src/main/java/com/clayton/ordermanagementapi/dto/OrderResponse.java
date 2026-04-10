package com.clayton.ordermanagementapi.dto;

import com.clayton.ordermanagementapi.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

    private Long id;
    private Status status;

}
