package com.backoffice.admin.dto;

import com.backoffice.admin.entity.AdminRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 역할 변경용
@Getter
@NoArgsConstructor
public class AdminRoleUpdateDto {
  @NotNull(message = "변경할 역할은 필수입니다.")
  private AdminRole role;
}
