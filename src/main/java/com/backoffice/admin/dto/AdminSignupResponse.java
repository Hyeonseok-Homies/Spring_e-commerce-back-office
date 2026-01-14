package com.backoffice.admin.dto;

import com.backoffice.admin.entity.AdminRole;
import com.backoffice.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminSignupResponse {

  private final Long id;
  private final String name;
  private final String email;
  private final String phoneNumber;
  private final AdminRole role; // enum 변경으로 인한 변수 변경
  private final AdminStatus status; // enum 변경으로 인한 변수 변경
  private final LocalDateTime createdAt;
  private final LocalDateTime approvedAt;

  public AdminSignupResponse(
      Long id,
      String name,
      String email,
      String phoneNumber,
      AdminRole role,
      AdminStatus status,
      LocalDateTime createdAt,
      LocalDateTime modifiedAt) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.role = role;
    this.status = status;
    this.createdAt = createdAt;
    this.approvedAt = getApprovedAt();
  }
}
