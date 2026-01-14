package com.backoffice.admin.service;

import com.backoffice.admin.dto.AdminLoginResponse;
import com.backoffice.admin.dto.AdminLoginRequest;
import com.backoffice.admin.entity.Admin;
import com.backoffice.admin.repository.AdminRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
  private final AdminRepository adminRepository;

  // private final PasswordEncoder passwordEncoder;

  @Transactional(readOnly = true)
  public AdminLoginResponse login(@Valid AdminLoginRequest request) {
    Admin admin =
        adminRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 이메일입니다."));
    // if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
    //    throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
    // }
    switch (admin.getStatus()) {
      case INACTIVE -> throw new IllegalStateException("계정이 비활성 상태입니다.");
      case SUSPENDED -> throw new IllegalStateException("계정이 정지 상태입니다.");
      case PENDING -> throw new IllegalStateException("계정이 승인대기 상태입니다.");
      case REJECTED -> throw new IllegalStateException("계정이 거부 상태입니다.");
    }

    return new AdminLoginResponse(
        admin.getId(), admin.getName(), admin.getStatus(), admin.getRole(), admin.getEmail());
  }
}
