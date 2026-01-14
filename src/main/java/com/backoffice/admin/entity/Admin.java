package com.backoffice.admin.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) // 생성,수정일 자동화
public class Admin extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // @OneToMany(mappedBy = "Product", cascade = CascadeType.ALL, orphanRemoval = true)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  private AdminRole role;

  @Enumerated(EnumType.STRING)
  private AdminStatus status;

  private LocalDateTime approvedAt;
  private LocalDateTime rejectedAt;

  private String rejectionReason;

  // 회원가입
  public Admin(String name, String email, String password, String phoneNumber, AdminRole role) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.role = role;
    this.status = AdminStatus.PENDING;
  }

  // 관리자 정보 수정
  public void updateInfo(Admin admin) {
    this.name = admin.name;
    this.email = admin.email;
    this.phoneNumber = admin.phoneNumber;
  }

  // 관리자 역할 변경
  public void approve() {
    if (this.status != AdminStatus.PENDING) {
      throw new IllegalStateException("승인 대기 상태인 관리자만 승인할수 있습니다.");
    }
    this.status = AdminStatus.ACTIVE;
    this.approvedAt = LocalDateTime.now();
  }

  // 거부 처리
  public void reject(String reason) {
    if (this.status != AdminStatus.PENDING) {
      throw new IllegalStateException("승인 대기 상태인 관리자만 승인할수 있습니다.");
    }
    this.status = AdminStatus.REJECTED;
    this.rejectedAt = LocalDateTime.now();
    this.rejectionReason = reason;
  }
}
