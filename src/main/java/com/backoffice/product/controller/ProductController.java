package com.backoffice.product.controller;

import com.backoffice.product.dto.*;
import com.backoffice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping("/products")
  public ResponseEntity<ProductCreateResponse> create(@RequestBody ProductCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
  }

  @GetMapping("/products")
  public ResponseEntity<List<ProductGetResponse>> getAll() {
      return ResponseEntity.status(HttpStatus.OK).body(productService.getAll());
  }

}
