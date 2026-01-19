package com.backoffice.admin.dto;

import com.backoffice.admin.entity.AdminRole;
import com.backoffice.admin.entity.AdminStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminApprovalRequestDto {
  @NotNull(message = "승인 시 부여할 관리자 역할은 필수 입니다.")
  private AdminStatus status;
}
