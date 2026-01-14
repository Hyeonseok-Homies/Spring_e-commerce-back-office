package com.backoffice.admin.repository;

import com.backoffice.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

  // 이메일 중복 체크
  boolean existsByEmail(String email);
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
  Optional<Admin> findByEmail(String email);
}
