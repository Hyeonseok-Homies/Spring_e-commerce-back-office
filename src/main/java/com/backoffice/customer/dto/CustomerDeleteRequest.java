package com.backoffice.customer.dto;

import lombok.Getter;

@Getter
public class CustomerDeleteRequest {

    private String email;
    private String password;
}
