package com.backoffice.product.controller;

import com.backoffice.product.dto.*;
import com.backoffice.product.entity.ProductStatus;
import com.backoffice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping("/products")
  public ResponseEntity<ProductCreateResponse> create(@RequestBody ProductCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
  }

  @GetMapping("/products")
  public ResponseEntity<Page<ProductGetResponse>> getAll(
      @RequestParam String name,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10 ") Integer size,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String sortOrder,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) ProductStatus status) {

    return ResponseEntity.status(HttpStatus.OK)
        .body(productService.getAll(name, page, size, sortBy, sortOrder, category, status));
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<ProductGetResponse> getOne(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(productService.getOne(id));
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<ProductUpdateResponse> update(
      @PathVariable Long id, @RequestBody ProductUpdateRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(productService.update(id, request));
  }

  @DeleteMapping("/products/{id}")
  public void delete(@PathVariable Long id) {
    productService.delete(id);
  }
}
