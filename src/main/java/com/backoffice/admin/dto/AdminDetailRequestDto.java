package com.backoffice.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminDetailRequestDto {
  @NotNull(message = "조회 대상 ID는 필수입니다.")
  private Long adminId; // 조회 대상 ID
}
