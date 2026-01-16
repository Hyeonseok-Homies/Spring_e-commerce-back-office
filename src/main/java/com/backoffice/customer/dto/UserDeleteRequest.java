package com.backoffice.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDeleteRequest {

  @NotBlank(message = "이메일은 필수 입력 사항입니다.")
  @Email
  private String email;
}
