package com.bestcommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/home")
  public String showHome() {
    return "Welcome to BestCommerce";
  }
}
