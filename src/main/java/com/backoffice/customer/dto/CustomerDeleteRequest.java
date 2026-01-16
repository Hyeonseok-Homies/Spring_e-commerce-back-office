package com.backoffice.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CustomerDeleteRequest {

  @NotBlank(message = "이메일은 필수 입력 항목입니다.")
  @Email
  private String email;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  private String password;
}
