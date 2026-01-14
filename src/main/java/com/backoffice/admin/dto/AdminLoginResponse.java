package com.backoffice.admin.dto;

import lombok.Getter;

@Getter
public class AdminLoginResponse {
  private final Long id;
  private final String name;
  private final String status;
  private final String role;
  private final String email;

  public AdminLoginResponse(Long id, String name, String status, String role, String email) {
    this.id = id;
    this.name = name;
    this.status = status;
    this.role = role;
    this.email = email;
  }
}
