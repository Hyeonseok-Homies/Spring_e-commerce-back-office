package com.backoffice.admin.dto;

import com.backoffice.admin.entity.AdminRole;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class AdminSignupRequest {

  // 필수값 누락에 대한 에러처리
  @NotBlank(message = "이름은 필수 입력 사항입니다.")
  private String name;

  // 필수값 누락 및 형식 오류에 대한 에러처리
  @NotBlank(message = "이메일은 필수 입력 사항입니다.")
  @Email(message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  // 필수값 누락 및 형식 오류에 대한 에러처리
  @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
  @Size(min = 8, message = "비밀번호는 8자 이상으로 설정해주셔야 합니다.")
  private String password;

  // 필수값 누락 및 형식 오류에 대한 에러처리
  @NotBlank(message = "전화번호는 필수 입력 사항입니다.")
  @Pattern(regexp = "^010-?\\d{4}-?\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
  private String phoneNumber;

  // 필수값 누락에 대한 에러처리
  @NotNull(message = "역할은 필수 입력 사항입니다.")
  private AdminRole role;
}
