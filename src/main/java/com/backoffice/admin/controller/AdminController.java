package com.backoffice.admin.controller;

import com.backoffice.admin.dto.AdminLoginResponse;
import com.backoffice.admin.dto.AdminRequestLogin;
import com.backoffice.admin.dto.SessionAdmin;
import com.backoffice.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final  AdminService adminService;

    @PostMapping("/admins/login")
    public ResponseEntity<String> login(@Valid @RequestBody AdminRequestLogin request, HttpSession session) {
        AdminLoginResponse result = adminService.login(request);
        SessionAdmin sessionAdmin = new SessionAdmin(
                result.getId(),
                result.getEmail(),
                result.getRole()
        );
        session.setAttribute("loginAdmin", sessionAdmin);
        return ResponseEntity.ok().body("로그인 완료");
    }
}
