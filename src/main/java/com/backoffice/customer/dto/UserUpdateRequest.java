package com.backoffice.customer.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequest {

    private String name;
    private String email;
    private String phoneNumber;
}
