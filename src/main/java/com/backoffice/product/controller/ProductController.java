package com.backoffice.product.controller;

import com.backoffice.admin.config.Login;
import com.backoffice.admin.dto.SessionAdmin;
import com.backoffice.admin.service.AdminService;
import com.backoffice.product.dto.*;
import com.backoffice.product.entity.ProductStatus;
import com.backoffice.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductCreateResponse> create(
      @Login SessionAdmin sessionAdmin,
      @Valid @RequestBody ProductCreateRequest request,
      HttpServletRequest httpRequest) {

    Long adminId = sessionAdmin.getId();

    return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request, adminId));
  }

  @GetMapping
  public ResponseEntity<Page<ProductGetResponse>> getAll(
      @Login SessionAdmin sessionAdmin,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) ProductStatus status,
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
          Pageable pageable) {

    // 검색 조건(name, category, status)과 페이징 정보(pageable)를 서비스에 전달
    return ResponseEntity.status(HttpStatus.OK)
        .body(productService.getAll(name, category, status, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductGetResponse> getOne(
      @Login SessionAdmin sessionAdmin, @PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(productService.getOne(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductUpdateResponse> update(
      @Login SessionAdmin sessionAdmin,
      @PathVariable Long id,
      @Valid @RequestBody ProductUpdateRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(productService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public void delete(@Login SessionAdmin sessionAdmin, @PathVariable Long id) {
    productService.delete(id);
  }
}
