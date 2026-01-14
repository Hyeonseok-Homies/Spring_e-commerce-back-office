package com.backoffice.admin.repository;

import com.backoffice.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

  // 이메일 중복 체크
  boolean existsByEmail(String email);

  Optional<Admin> findByEmail(String email);
}
