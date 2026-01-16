package com.backoffice.customer.dto;

import com.backoffice.customer.entity.CustomerStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CustomerGetRequest {

  private String keyword; // 이름, 이메일

  @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
  private int page = 1;

  @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
  @Max(value = 100, message = "페이지 크기는 최대 100까지 가능합니다.")
  private int pageSize = 10;

  @NotBlank(message = "정렬 기준은 필수입니다.")
  private String sortBy = "createdAt";

  @NotBlank(message = "정렬 방향은 필수입니다.")
  private String direction = "desc";

  CustomerStatus status; // 상태 enum status

  // 상태 필터는 세션에서 가져와야할 것 같음..
  // sortBy 랑 direction 을 확인하고 꼭 그 값에 맞춰서 진행 하면됨
  // 아무것도 없는 값을 줄 가능성도 있으니까 기본값으로 갈 수 있게!!
  // 클라이언트가 넣은 값으로 하면 덮어씌워질 수 있음

}
