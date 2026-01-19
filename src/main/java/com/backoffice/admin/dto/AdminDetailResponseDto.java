package com.backoffice.admin.dto;

import com.backoffice.admin.entity.Admin;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminDetailResponseDto {
  private final Long id;
  private final String name;
  private final String email;
  private final String role;
  private final String status;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final String phoneNumber;
  private final LocalDateTime approvedAt;

  public AdminDetailResponseDto(Admin admin) {
    this.id = admin.getId();
    this.name = admin.getName();
    this.email = admin.getEmail();
    this.role = admin.getRole().name();
    this.status = admin.getStatus().name();
    this.createdAt = admin.getCreatedAt();
    this.updatedAt = admin.getUpdatedAt();
    this.phoneNumber = admin.getPhoneNumber();
    this.approvedAt = admin.getApprovedAt();
  }
}
