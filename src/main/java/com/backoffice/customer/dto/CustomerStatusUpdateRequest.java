package com.backoffice.customer.dto;

import com.backoffice.customer.entity.CustomerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CustomerStatusUpdateRequest {

  @NotNull(message = "변경할 상태는 필수입니다.")
  private CustomerStatus status;
}
