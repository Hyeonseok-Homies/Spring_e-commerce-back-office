package com.backoffice.admin.controller;

import com.backoffice.admin.config.Login;
import com.backoffice.admin.dto.*;
import com.backoffice.admin.entity.Admin;
import com.backoffice.admin.entity.AdminRole;
import com.backoffice.admin.entity.AdminStatus;
import com.backoffice.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  // 회원가입 controller
  @PostMapping("/signup")
  public ResponseEntity<AdminSignupResponse> signup(
      @Valid @RequestBody AdminSignupRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(adminService.save(request));
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(
      @Valid @RequestBody AdminLoginRequest request, HttpSession session) {
    AdminLoginResponse result = adminService.login(request);
    // 세션에 id, email, role 저장
    SessionAdmin sessionAdmin =
        new SessionAdmin(result.getId(), result.getEmail(), result.getRole());
    session.setAttribute("loginAdmin", sessionAdmin);
    // 세션 생명주기 24시간
    session.setMaxInactiveInterval(86400);
    return ResponseEntity.ok().body("로그인 완료");
  }

  @GetMapping("/logout")
  public ResponseEntity<Void> logout(@Login SessionAdmin sessionAdmin, HttpSession session) {
    session.invalidate();
    return ResponseEntity.ok().build();
  }

  // ------------------전민우------------------
  // ================= [내 프로필 관리 API] =================

  @GetMapping("/me")
  public ResponseEntity<AdminDetailResponseDto> getMyProfile(
      @Login SessionAdmin sessionAdmin, HttpSession session) {

    return ResponseEntity.ok(adminService.getAdminDetail(sessionAdmin.getId()));
  }

  @PatchMapping("/me")
  public ResponseEntity<AdminResponseDto> updateMyProfile(
      @Login SessionAdmin sessionAdmin,
      HttpSession session, // 세션 주입
      @Valid @RequestBody AdminUpdateRequestDto requestDto) {

    return ResponseEntity.ok(adminService.updateMyProfile(sessionAdmin.getId(), requestDto));
  }

  @PatchMapping("/me/password")
  public ResponseEntity<Void> changePassword(
      @Login SessionAdmin sessionAdmin,
      HttpSession session, // 세션 주입
      @Valid @RequestBody PasswordChangeRequestDto requestDto) {

    adminService.changePassword(sessionAdmin.getId(), requestDto.getNewPassword());
    return ResponseEntity.noContent().build();
  }

  // ================= [슈퍼 관리자용 관리 API] =================

  // 1. 관리자 목록 조회
  @GetMapping
  public ResponseEntity<AdminListResponseDto> getAdminList(
      @Login SessionAdmin sessionAdmin, @ModelAttribute AdminListRequestDto requestDto) {
    return ResponseEntity.ok(adminService.getAdminList(requestDto));
  }

  // 2. 관리자 상세 조회
  @GetMapping("/{id}")
  public ResponseEntity<AdminDetailResponseDto> getAdminDetail(
      @Login SessionAdmin sessionAdmin, @PathVariable Long id) {
    return ResponseEntity.ok(adminService.getAdminDetail(id));
  }

  // 3. 관리자 정보 수정 (관리자에 의한 수정)
  @PatchMapping("/{id}")
  public ResponseEntity<AdminResponseDto> updateAdmin(
      @Login SessionAdmin sessionAdmin,
      @PathVariable Long id,
      @Valid @RequestBody AdminUpdateRequestDto requestDto) {
    return ResponseEntity.ok(adminService.updateAdmin(id, requestDto));
  }

  // 4. 가입 승인
  @PostMapping("/{id}/approve")
  public ResponseEntity<AdminApprovalResponseDto> approveAdmin(
      @Login SessionAdmin sessionAdmin,
      @PathVariable Long id,
      @Valid @RequestBody AdminApprovalRequestDto requestDto) {
    return ResponseEntity.ok(adminService.approveAdmin(id, requestDto.getRole()));
  }

  // 5. 가입 거절
  @PostMapping("/{id}/reject")
  public ResponseEntity<AdminRejectResponseDto> rejectAdmin(
      @Login SessionAdmin sessionAdmin,
      @PathVariable Long id,
      @Valid @RequestBody AdminRejectRequestDto requestDto) {
    return ResponseEntity.ok(adminService.rejectAdmin(id, requestDto));
  }

  // 6. 관리자 역할 변경
  @PatchMapping("/{id}/role")
  public ResponseEntity<Void> changeAdminRole(
      @Login SessionAdmin sessionAdmin,
      @PathVariable Long id,
      @Valid @RequestBody AdminRoleUpdateDto requestDto) {
    adminService.changeAdminRole(id, requestDto.getRole());
    return ResponseEntity.noContent().build();
  }

  // 7. 관리자 상태 변경
  @PatchMapping("/{id}/status")
  public ResponseEntity<Void> changeStatus(
      @Login SessionAdmin sessionAdmin,
      @PathVariable Long id,
      @Valid @RequestBody AdminStatusUpdateDto requestDto) {
    adminService.changeStatus(id, requestDto.getStatus());
    return ResponseEntity.noContent().build();
  }

  // 8. 관리자 삭제 (탈퇴)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAdmin(@Login SessionAdmin sessionAdmin, @PathVariable Long id) {
    adminService.deleteAdmin(id);
    return ResponseEntity.noContent().build();
  }
  // ------------------전민우------------------
}
