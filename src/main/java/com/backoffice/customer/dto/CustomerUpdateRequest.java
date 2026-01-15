package com.backoffice.customer.dto;

import lombok.Getter;

@Getter
public class CustomerUpdateRequest {

  private String name;
  private String email;
  private String phoneNumber;
  private String status;
}
