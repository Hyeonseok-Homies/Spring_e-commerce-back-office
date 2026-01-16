package com.backoffice.admin.entity;

import com.backoffice.Error.BadRequestException;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "admins")
@SuperBuilder // 1. 상속 관계 빌더
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 2. JPA용 기본 생성자
@AllArgsConstructor // 3. SuperBuilder와 호환되는 전체 생성자
@EntityListeners(AuditingEntityListener.class)
public class Admin extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // @OneToMany(mappedBy = "Product", cascade = CascadeType.ALL, orphanRemoval = true)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(nullable = false, length = 13)
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private AdminRole role;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private AdminStatus status;

  private LocalDateTime approvedAt;
  private LocalDateTime rejectedAt;
  @Column(length = 100)
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
  public void updateInfo(String name, String email, String phoneNumber) {
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  // 관리자 역할 변경
  public void approve(AdminRole role) {
    if (this.status != AdminStatus.PENDING) {
      throw new IllegalStateException("승인 대기 상태인 관리자만 승인할수 있습니다.");
    }
    this.status = AdminStatus.ACTIVE;
    this.approvedAt = LocalDateTime.now();
    this.role = role;
  }

  // 거부 처리
  public void reject(String reason) {
    if (this.status != AdminStatus.PENDING) { // 상태가 대기가 아니면
      throw new IllegalStateException("승인 대기 상태인 관리자만 거부할수 있습니다.");
    }
    if (reason == null || reason.isBlank()) { // 8. 요구사항: 거부 사유 필수 입력 항목
      throw new BadRequestException("거부 사유는 필수입니다.");
    }
    this.status = AdminStatus.REJECTED; // 거부 상태로 변경
    this.rejectedAt = LocalDateTime.now(); // 수정시간
    this.rejectionReason = reason; // 사유 저장
  }

  // 관리자 역할 변경
  public void changedRole(AdminRole newRole) {
    if (newRole == null) { // 역할이 null이면
      throw new BadRequestException("변경할 역할은 필수 입니다.");
    }
    this.role = newRole; // 새로운 역할을 저장
  }

  // 관리자 상태 변경
  public void changedStatus(AdminStatus newStatus) {
    if (newStatus == null) { // 새로운 상태가 null이면
      throw new BadRequestException("변경할 상태는 필수 입니다.");
    }
    this.status = newStatus; // 새로운 상태 저장
  }

  // 비밀번호 변경
  public void changePassword(String newPassword) {
    if (newPassword == null || newPassword.isBlank()) { // 새로운 패스워드가 null이거나 비어있다면
      throw new BadRequestException("새 비밀번호는 필수 입력 항목입니다.");
    }
    this.password = newPassword; // 비밀번호 변경
  }
}
