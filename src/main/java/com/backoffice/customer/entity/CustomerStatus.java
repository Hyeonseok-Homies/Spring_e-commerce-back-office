package com.backoffice.customer.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor  // final에게만 생성자 부여
public enum CustomerStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    SUSPENDED("정지");

    private final String value;

}