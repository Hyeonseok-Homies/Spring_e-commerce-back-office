package com.backoffice.admin.dto;

import com.backoffice.admin.entity.AdminRole;
import com.backoffice.admin.entity.AdminStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

// 필요없어짐
@Getter
public class AdminListRequestDto {
  // 아래 셋팅된 기본값은, 요청시 별도의 값이 없다면 기본값으로 활용
  private String keyword;

  @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
  private int page = 1;

  @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
  @Max(value = 100, message = "페이지 크기는 최대 100까지 가능합니다.")
  private int pageSize = 10;

  @NotBlank(message = "정렬 기준은 필수입니다.")
  private String sortBy = "createdAt";

  @NotBlank(message = "정렬 방향은 필수입니다.")
  private String direction = "desc";

  private AdminRole role;
  private AdminStatus status;
}
