package com.backoffice.admin.service;

import com.backoffice.admin.dto.AdminSignupRequest;
import com.backoffice.admin.dto.AdminSignupResponse;
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

    @Transactional
    public AdminSignupResponse save(AdminSignupRequest request) {
        Admin admin = new Admin(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhoneNumber(),
                request.getRole()
        );
        Admin savedAdmin = adminRepository.save(admin);
        return new AdminSignupResponse(
                savedAdmin.getId(),
                savedAdmin.getName(),
                savedAdmin.getEmail(),
                savedAdmin.getPhoneNumber(),
                savedAdmin.getRole(),
                savedAdmin.getStatus(),
                savedAdmin.getCreatedAt(),
                savedAdmin.getModifiedAt()
        );
    }
}
