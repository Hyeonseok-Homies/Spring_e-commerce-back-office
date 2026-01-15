package com.backoffice.admin.controller;

import com.backoffice.admin.dto.*;
import com.backoffice.admin.entity.Admin;
import com.backoffice.admin.entity.AdminRole;
import com.backoffice.admin.entity.AdminStatus;
import com.backoffice.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ================= [내 프로필 관리 API] =================
    //정적 경로(/me)를 변수 경로(/{id})보다 상단에 두어 경로 충돌을 방지함

    @GetMapping("/me")
    public ResponseEntity<AdminDetailResponseDto> getMyProfile(HttpSession session) {
        AdminSessionDto adminSession = (AdminSessionDto) session.getAttribute("LOGIN_ADMIN");

        if (adminSession == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        return ResponseEntity.ok(adminService.getAdminDetail(adminSession.getId()));
    }

    @PatchMapping("/me")
    public ResponseEntity<AdminResponseDto> updateMyProfile(
            HttpSession session, // 세션 주입
            @Valid @RequestBody AdminUpdateRequestDto requestDto) {

        // 세션 키("LOGIN_ADMIN")로 정보를 꺼내기 팀원 내용확인하기
        AdminSessionDto adminSession = (AdminSessionDto) session.getAttribute("LOGIN_ADMIN");

        // 로그인 체크 팀원님이 구현해두었으면 생략가능
        if (adminSession == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(adminService.updateMyProfile(adminSession.getId(), requestDto));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            HttpSession session, // 세션 주입
            @Valid @RequestBody PasswordChangeRequestDto requestDto) {

        // 세션 키("LOGIN_ADMIN")로 정보를 꺼내기 팀원 내용확인하기
        AdminSessionDto adminSession = (AdminSessionDto) session.getAttribute("LOGIN_ADMIN");

        // 로그인 체크 팀원님이 구현해두었으면 생략가능
        if (adminSession == null) {
            return ResponseEntity.status(401).build();
        }

        adminService.changePassword(adminSession.getId(), requestDto.getNewPassword());
        return ResponseEntity.noContent().build();
    }

    // ================= [슈퍼 관리자용 관리 API] =================

    // 1. 관리자 목록 조회
    @GetMapping
    public ResponseEntity<AdminListResponseDto> getAdminList(@ModelAttribute AdminListRequestDto requestDto) {
        return ResponseEntity.ok(adminService.getAdminList(requestDto));
    }

    // 2. 관리자 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<AdminDetailResponseDto> getAdminDetail(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdminDetail(id));
    }

    // 3. 관리자 정보 수정 (관리자에 의한 수정)
    @PatchMapping("/{id}")
    public ResponseEntity<AdminResponseDto> updateAdmin(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdateRequestDto requestDto) {
        return ResponseEntity.ok(adminService.updateAdmin(id, requestDto));
    }

    // 4. 가입 승인
    @PostMapping("/{id}/approve")
    public ResponseEntity<AdminApprovalResponseDto> approveAdmin(
            @PathVariable Long id,
            @Valid @RequestBody AdminApprovalRequestDto requestDto) {
        return ResponseEntity.ok(adminService.approveAdmin(id, requestDto.getRole()));
    }

    // 5. 가입 거절
    @PostMapping("/{id}/reject")
    public ResponseEntity<AdminRejectResponseDto> rejectAdmin(
            @PathVariable Long id,
            @Valid @RequestBody AdminRejectRequestDto requestDto) {
        return ResponseEntity.ok(adminService.rejectAdmin(id, requestDto));
    }

    // 6. 관리자 역할 변경
    @PatchMapping("/{id}/role")
    public ResponseEntity<Void> changeAdminRole(
            @PathVariable Long id,
            @Valid @RequestBody AdminRoleUpdateDto requestDto) {
        adminService.changeAdminRole(id, requestDto.getRole());
        return ResponseEntity.noContent().build();
    }

    // 7. 관리자 상태 변경
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody AdminStatusUpdateDto requestDto) {
        adminService.changeStatus(id, requestDto.getStatus());
        return ResponseEntity.noContent().build();
    }

    // 8. 관리자 삭제 (탈퇴)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}