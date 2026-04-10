package com.clayton.ordermanagementapi.dto;

import com.clayton.ordermanagementapi.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {

    @NotNull
    private Status status;

}
