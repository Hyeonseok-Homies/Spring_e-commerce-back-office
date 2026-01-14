package com.backoffice.admin.controller;

import com.backoffice.admin.dto.AdminLoginResponse;
import com.backoffice.admin.dto.AdminLoginRequest;
import com.backoffice.admin.dto.SessionAdmin;
import com.backoffice.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class AdminController {
  private final AdminService adminService;

  @PostMapping("/admins/login")
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

  @PostMapping("/admins/logout")
  public ResponseEntity<Void> logout(
      @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
      HttpSession session) {
    if (sessionAdmin == null) {
      return ResponseEntity.badRequest().build();
    }
    session.invalidate();
    return ResponseEntity.ok().build();
  }
}
