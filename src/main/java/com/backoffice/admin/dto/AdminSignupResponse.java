package com.backoffice.admin.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminSignupResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final String role;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public AdminSignupResponse(Long id, String name, String email, String phoneNumber, String role, String status, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
