package com.bestcommerce.controller;

import com.bestcommerce.model.product.Product;
import com.bestcommerce.model.product.ProductWrapper;
import com.bestcommerce.service.LoginService;
import com.bestcommerce.service.MessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MerchantProductsController {

  private final MessagingService rabbitMessageSendingService;

  private final LoginService loginService;

  @PostMapping(value = "/saveproduct")
  public ResponseEntity<?> saveProduct(@RequestBody Product product) {

    if (product.getInventory() < 5) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("Products inventory should be at least 5");
    } else {
      product.setMerchantId(loginService.currentLoggedInUserId());

      return ResponseEntity.status(HttpStatus.CREATED)
          .body(rabbitMessageSendingService.sendProduct(product));
    }
  }

  @GetMapping("/merchant/products")
  public ResponseEntity<?> getMerchantProducts(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {

    Long merchantId = loginService.currentLoggedInUserId();

    if (sortBy.equals("unitPrice") || sortBy.equals("inventory") || sortBy.equals("id")) {
      ProductWrapper productWrapper =
          rabbitMessageSendingService.sendId(merchantId, pageNo, pageSize, sortBy);

      return ResponseEntity.status(HttpStatus.OK).body(productWrapper);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("Products cannot be listed by " + sortBy);
    }
  }
}
