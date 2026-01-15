package com.backoffice.admin.dto;

import com.backoffice.admin.entity.Admin;
import lombok.Getter;

@Getter
public class AdminResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final String role;
    private final String status;
    private final String phoneNumber;

    //서비스에서 엔티티를 이 DTO로 변환할 때 사용
    public AdminResponseDto(Admin admin) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.role = admin.getRole().name();
        this.status = admin.getStatus().name();
        this.phoneNumber = admin.getPhoneNumber();
    }
}
