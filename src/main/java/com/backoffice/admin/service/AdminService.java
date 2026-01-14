package com.backoffice.admin.service;

import com.backoffice.admin.dto.AdminLoginResponse;
import com.backoffice.admin.dto.AdminRequestLogin;
import com.backoffice.admin.entity.Admin;
import com.backoffice.admin.repository.AdminRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminLoginResponse login(@Valid AdminRequestLogin request) {
        Admin admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 이메일입니다.")
        );
        //if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
        //    throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        //}
        return new AdminLoginResponse(
                admin.getId(),
                admin.getName(),
                admin.getStatus(),
                admin.getRole(),
                admin.getEmail()
        );
    }
}
