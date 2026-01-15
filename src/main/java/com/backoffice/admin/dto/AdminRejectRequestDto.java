package com.backoffice.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminRejectRequestDto {
  @NotBlank(message = "거부 사유는 반드시 입력 해야 합니다.")
  private String reason;
}
