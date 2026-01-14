package com.backoffice.admin.service;

import com.backoffice.admin.config.PasswordEncoder;
import com.backoffice.admin.dto.AdminSignupRequest;
import com.backoffice.admin.dto.AdminSignupResponse;
import com.backoffice.admin.entity.Admin;
import com.backoffice.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final AdminRepository adminRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public AdminSignupResponse save(AdminSignupRequest request) {
    // 이메일 중복 체크
    if (adminRepository.existsByEmail(request.getEmail())) {
      throw new IllegalStateException("이미 사용 중인 이메일입니다.");
    }
    // 비밀번호 암호화
    String encodedPassword = passwordEncoder.encode(request.getPassword());
    // 승인대기 status는 Admin 생성자에서 자동 세팅
    Admin admin =
        new Admin(
            request.getName(),
            request.getEmail(),
            encodedPassword, // 암호화된 비밀번호
            request.getPhoneNumber(),
            request.getRole());
    Admin savedAdmin = adminRepository.save(admin);
    return new AdminSignupResponse(
        savedAdmin.getId(),
        savedAdmin.getName(),
        savedAdmin.getEmail(),
        savedAdmin.getPhoneNumber(),
        savedAdmin.getRole(),
        savedAdmin.getStatus(),
        savedAdmin.getCreatedAt(),
        savedAdmin.getApprovedAt());
  }
}
