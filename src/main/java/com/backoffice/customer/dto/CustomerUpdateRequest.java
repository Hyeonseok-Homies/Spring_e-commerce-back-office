package com.backoffice.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CustomerUpdateRequest {

  @NotBlank(message = "이름은 필수 입력 항목입니다.")
  private String name;

  @NotBlank(message = "이메일은 필수 입력 항목입니다.")
  @Email
  private String email;

  @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
  @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "010-0000-0000 형식이어야 합니다.")
  private String phoneNumber;

  @NotBlank(message = "상태값은 필수입니다.")
  private String status;
}
