package com.backoffice.admin.dto;

import com.backoffice.admin.entity.AdminStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 상태 변경용
@Getter
@NoArgsConstructor
public class AdminStatusUpdateDto {
    @NotNull(message = "변경할 상태는 필수입니다.")
    private AdminStatus status;
}