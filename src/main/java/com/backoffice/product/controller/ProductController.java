package com.backoffice.product.controller;

import com.backoffice.admin.config.Login;
import com.backoffice.admin.dto.SessionAdmin;
import com.backoffice.admin.service.AdminService;
import com.backoffice.product.dto.*;
import com.backoffice.product.entity.ProductStatus;
import com.backoffice.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
      @RequestBody ProductCreateRequest request,
      HttpServletRequest httpRequest) {

    Long adminId = sessionAdmin.getId();

    return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request, adminId));
  }

  @GetMapping
  public ResponseEntity<Page<ProductGetResponse>> getAll(
      @Login SessionAdmin sessionAdmin,
      @RequestParam(required = false) String name,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10 ") Integer size,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String sortOrder,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) ProductStatus status) {

    return ResponseEntity.status(HttpStatus.OK)
        .body(productService.getAll(name, page, size, sortBy, sortOrder, category, status));
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
      @RequestBody ProductUpdateRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(productService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public void delete(@Login SessionAdmin sessionAdmin, @PathVariable Long id) {
    productService.delete(id);
  }
}
