package com.backoffice.admin.controller;

import com.backoffice.admin.dto.AdminSignupRequest;
import com.backoffice.admin.dto.AdminSignupResponse;
import com.backoffice.admin.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/admins/signup")
    public ResponseEntity<AdminSignupResponse> signup(
            @Valid @RequestBody AdminSignupRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.save(request));
    }
}
