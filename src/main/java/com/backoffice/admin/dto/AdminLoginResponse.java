package com.backoffice.admin.dto;

import com.backoffice.admin.entity.AdminRole;
import com.backoffice.admin.entity.AdminStatus;
import lombok.Getter;

@Getter
public class AdminLoginResponse {
  private final Long id;
  private final String name;
  private final AdminStatus status;
  private final AdminRole role;
  private final String email;

  public AdminLoginResponse(
      Long id, String name, AdminStatus status, AdminRole role, String email) {
    this.id = id;
    this.name = name;
    this.status = status;
    this.role = role;
    this.email = email;
  }
}
