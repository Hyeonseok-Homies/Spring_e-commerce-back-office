package com.backoffice.customer.entity;

import jakarta.persistence.*;
import com.backoffice.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "customers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(nullable = false, length = 13)
  private String phoneNumber;

  @Column(nullable = false, length = 30)
  @Enumerated(EnumType.STRING)
  private CustomerStatus status;

  // 생성자
  public Customer(String name, String email, String phoneNumber, CustomerStatus status) {
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.status = status;
  }

  // 고객 정보 수정
  public void update(String name, String email, String phoneNumber) {
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  // 고객 상태 변경
  public void updateStatus(CustomerStatus status) {
    this.status = status;
  }
}
