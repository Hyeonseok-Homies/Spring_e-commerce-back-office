package com.backoffice.admin.service;

import com.backoffice.admin.config.PasswordEncoder;
import com.backoffice.admin.dto.AdminSignupRequest;
import com.backoffice.admin.dto.AdminSignupResponse;
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

  @Transactional(readOnly = true)
  public AdminLoginResponse login(@Valid AdminLoginRequest request) {
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
