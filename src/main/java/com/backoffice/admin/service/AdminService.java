package com.backoffice.admin.service;

import com.backoffice.admin.config.PasswordEncoder;
import com.backoffice.admin.dto.*;
import com.backoffice.admin.entity.Admin;
import com.backoffice.admin.entity.AdminRole;
import com.backoffice.admin.entity.AdminStatus;
import com.backoffice.admin.entity.AdminRole;
import com.backoffice.admin.entity.AdminStatus;
import com.backoffice.admin.repository.AdminRepository;
import jakarta.validation.Valid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

  @Transactional(readOnly = true)
  public AdminLoginResponse login(AdminLoginRequest request) {
    Admin admin =
        adminRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 이메일입니다."));
    if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
      throw new NoSuchElementException("비밀번호가 일치하지 않습니다.");
    }
    switch (admin.getStatus()) {
      case INACTIVE -> throw new IllegalStateException("계정이 비활성 상태입니다.");
      case SUSPENDED -> throw new IllegalStateException("계정이 정지 상태입니다.");
      case PENDING -> throw new IllegalStateException("계정이 승인대기 상태입니다.");
      case REJECTED -> throw new IllegalStateException("계정이 거부 상태입니다.");
    }

    return new AdminLoginResponse(
        admin.getId(), admin.getName(), admin.getStatus(), admin.getRole(), admin.getEmail());
  }

  // ----------------전민우----------------
  // 1. [관리자 리스트 조회] 검색, 페이징, 역할/상태 필터 적용
  public AdminListResponseDto getAdminList(AdminListRequestDto requestDto) {
    // 검색 규칙 설정
    Pageable pageable =
        PageRequest.of( // 아래 내용들을 모두 챙겨 하나로 묶어 new Pageable 객체를 생성하는것????????
            requestDto.getPage() - 1, // 1페이라고 입력이 와도 0번째 인덱스부터 접근???????->-1이면 어떻게 하지?
            // 프론트와 1을 보낼지 0을 보낼지 명세서로 약속을 해야한다
            requestDto.getPageSize(),
            Sort.by(Sort.Direction.fromString(requestDto.getDirection()), requestDto.getSortBy())
            // 정렬방향 Sort.Direction.fromString(requestDto.getDirection())
            // requestDto.getSortBy() 어떤 필드를 기준으로 정렬할지
            );

    // 검색어와 역할/상태 필터를 적용한 조회
    Page<Admin> adminPage =
        adminRepository.searchAdmins( // searchAdmins 만든 검색 규칙
            requestDto.getKeyword(), requestDto.getRole(), requestDto.getStatus(), pageable);

    // 엔티티->DTO 변환
    List<AdminResponseDto> list =
        adminPage.getContent().stream() // .getContent() Admin 객체를 꺼내서 .stream()하나씩
            .map(AdminResponseDto::new) // 매핑해라 새로운 AdminResponseDto객체를 만들어서 Admin를 넣어라
            .collect(Collectors.toList()); // 변환된 AdminResponseDto객체를 한바구니에 담아라 ->List

    return new AdminListResponseDto(
        list,
        adminPage.getNumber() + 1,
        adminPage.getTotalPages(),
        adminPage.getTotalElements(),
        adminPage.getSize(),
        adminPage.hasNext());
  }

  // 2. [관리자 상세 조회]
  public AdminDetailResponseDto getAdminDetail(Long adminId) {
    Admin admin =
        adminRepository
            .findById(adminId)
            .orElseThrow(() -> new IllegalArgumentException("해당 관리자가 없습니다."));
    return new AdminDetailResponseDto(admin);
  }

  // 3. [관리자 승인] 승인대기 상태 확인 후 활성화 및 승인일시 업데이트
  @Transactional
  public AdminApprovalResponseDto approveAdmin(Long adminId, AdminRole role) {
    Admin admin =
        adminRepository
            .findById(adminId)
            .orElseThrow(() -> new IllegalArgumentException("해당 관리자가 없습니다."));
    admin.approve(role); // 엔티티 내부에서 상태변경 및 승인일시 처리
    return new AdminApprovalResponseDto(admin);
  }

  // 4. [관리자 거부] 승인대기 상태 확인 후 거부 상태 및 사유 업데이트
  @Transactional
  public AdminRejectResponseDto rejectAdmin(Long adminId, AdminRejectRequestDto requestDto) {
    Admin admin =
        adminRepository
            .findById(adminId)
            .orElseThrow(() -> new IllegalArgumentException("해당 관리자가 없습니다."));
    admin.reject(requestDto.getReason()); // 엔티티 내부에서 거부일시 및 필수 사유 처리
    return new AdminRejectResponseDto(admin);
  }

  // 5. [관리자 정보 수정] 이름, 이메일, 전화번호 수정
  @Transactional
  public AdminResponseDto updateAdmin(Long adminId, AdminUpdateRequestDto dto) {
    Admin admin =
        adminRepository
            .findById(adminId)
            .orElseThrow(() -> new IllegalArgumentException("해당 관리자가 없습니다."));
    admin.updateInfo(dto.getName(), dto.getEmail(), dto.getPhoneNumber());
    return new AdminResponseDto(admin);
  }

  // 6. [관리자 역할 변경]
  @Transactional
  public void changeAdminRole(Long adminId, AdminRole newRole) {
    Admin admin =
        adminRepository
            .findById(adminId)
            .orElseThrow(() -> new IllegalArgumentException("해당 관리자가 없습니다."));
    admin.changedRole(newRole);
  }

  // 7. [관리자 상태 변경] 활성, 비활성, 정지 등 상태값 변경
  @Transactional
  public void changeStatus(Long adminId, AdminStatus newStatus) {
    Admin admin =
        adminRepository
            .findById(adminId)
            .orElseThrow(() -> new IllegalArgumentException("해당 관리자가 없습니다."));
    admin.changedStatus(newStatus);
  }

  // 8. [관리자 삭제] 특정 관리자 탈퇴(삭제) 처리
  @Transactional
  public void deleteAdmin(Long adminId) {
    Admin admin =
        adminRepository
            .findById(adminId)
            .orElseThrow(() -> new IllegalArgumentException("해당 관리자가 없습니다."));
    adminRepository.delete(admin);
  }

  // 9. [내 프로필 조회] 로그인한 유저 정보 조회
  public AdminDetailResponseDto getMyProfile(Long currentAdminId) {
    return getAdminDetail(currentAdminId);
  }

  // 10. [내 프로필 수정] 내 정보 수정 (정보 수정 로직 재활용)
  @Transactional
  public AdminResponseDto updateMyProfile(Long currentAdminId, AdminUpdateRequestDto requestDto) {
    return updateAdmin(currentAdminId, requestDto);
  }

  // 11. [비밀번호 변경] 새로운 비밀번호로 업데이트
  @Transactional
  public void changePassword(Long adminId, String newPassword) {
    Admin admin =
        adminRepository
            .findById(adminId)
            .orElseThrow(() -> new IllegalArgumentException("해당 관리자가 없습니다."));
    admin.changePassword(newPassword); // 엔티티에 해당 메서드 구현 필요
  }
  // --------------전민우--------
}
