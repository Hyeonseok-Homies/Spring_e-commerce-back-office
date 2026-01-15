package com.backoffice.customer.dto;

import com.backoffice.customer.entity.CustomerStatus;
import lombok.Getter;

@Getter
public class CustomerStatusUpdateRequest {

    // Enum status 사용
    private CustomerStatus status;
}
