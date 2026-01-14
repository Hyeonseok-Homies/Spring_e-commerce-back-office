package com.backoffice.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AdminRequestLogin {
    @Email(message = "잘못된 이메일 형식입니다.")
    private String email;
    @Size(min = 8, max = 255)
    private String password;
}
