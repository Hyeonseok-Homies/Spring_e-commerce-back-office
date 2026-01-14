package com.backoffice.admin.dto;

import lombok.Getter;

@Getter
public class AdminSignupRequest {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;

}
