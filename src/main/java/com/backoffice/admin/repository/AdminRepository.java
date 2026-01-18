package com.backoffice.admin.repository;

import com.backoffice.admin.entity.Admin;
import com.backoffice.admin.entity.AdminRole;
import com.backoffice.admin.entity.AdminStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

  // 이메일 중복 체크
  boolean existsByEmail(String email);

  Optional<Admin> findByEmail(String email);

  // 검색 키워드(이름, 이메일), 역할 필터, 상태 필터를 모두 지원하는 동적 쿼리
  // 자바식으로 처리 가능 for stream->불필요한 자원낭비
  // 유연한사람->핵심 코어를 아는사람
  @Query(
      "SELECT a FROM Admin a WHERE "
          + // Admin 객체(테이블) a를 모두 객체 a 전체 조회하겠다. 어떻게?(조건)
          "(:kw IS NULL OR a.name LIKE %:kw% OR a.email LIKE %:kw%) AND "
          +
          // 외부(:) 변수(kw)[입력 검색어]에 값이 비어있나? or a테이블에 name(DB에 저장된 이름)이 있으면 LIKE(~같은지 봐라) kw앞뒤로 어떤내용이
          // 와도 상관없고(%)
          // 또는(OR) a테이블에 email이 LIKE(~같은지) kw앞뒤로 어떤내용이 와도 상관없다(%)
          // 즉, 입력한 키워드가 테이블에 있는 이름과 이메일이중에서 유사값이 있는지 찾아라
          "(:role IS NULL OR a.role = :role) AND "
          +
          // (:)외부에서 온 role이 널인지 또는 a테이블에 role이 입력 role과 일치하는지 그리고
          "(:status IS NULL OR a.status = :status)")
  // (:)외부에서 온 status가 널인지 또는 a테이블에 status가 입력 status과 일치하는지
  Page<Admin> searchAdmins(
      @Param("kw") String kw,
      // 한페이지 분량만 가져오는데 내용물은 <Admin>이다 + 메서드이름 +  @Param("kw"): 쿼리문 안에 적었던 :kw 자리와 이 변수 kw를 서로
      // 연결(바인딩)하라는 명령
      @Param("role") AdminRole role,
      @Param("status") AdminStatus status,
      Pageable pageable); // Pageable ->page:현재 몇번째 페이지 인지 size: 한페이지에 몇개씩 보여줄건지 sort: 정렬 기준

  // "몇 번째 페이지를 볼 건지", "한 페이지에 몇 명씩 보여줄 건지", "이름순으로 정렬할 건지" 같은 조회 옵션을 담고 있는 아주 중요한 객체
  // 1차 캐시에서 하는게 아니라 DB에서 직접 조회 한다
  // 리턴은 아래와 같이 리턴한다.
  /*return adminRepository.searchAdmins(
          requestDto.getKw(),
          requestDto.getRole(),
          requestDto.getStatus(),
  pageable
  );*/

  Optional<Admin> findByName(String name);
}
