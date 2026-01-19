package com.backoffice.admin.config;

import com.backoffice.admin.entity.Admin;
import com.backoffice.admin.entity.AdminRole;
import com.backoffice.admin.entity.AdminStatus;
import com.backoffice.admin.repository.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

  private final AdminRepository adminRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void run(String... args) {
    // 이미 계정이 있으면 더 안 만들게 방어 로직 (멱등성)
    if (!adminRepository.existsByEmail("admin@example.com")) {

      Admin admin =
          Admin.builder()
              .name("Admin")
              .email("admin@example.com")
              .password(passwordEncoder.encode("12345678"))
              .phoneNumber("010-1234-5678")
              .role(AdminRole.SUPER)
              .status(AdminStatus.ACTIVE)
              .createdAt(LocalDateTime.now())
              .build();

      adminRepository.save(admin);
      System.out.println("=== 슈퍼관리자 계정 생성 완료 (admin@example.com / 12345678) ===");
    }
  }
}
