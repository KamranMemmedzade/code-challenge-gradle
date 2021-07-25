package com.bestcommerce;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.bestcommerce.controller.HomeController;
import com.bestcommerce.controller.LoginController;
import com.bestcommerce.controller.MerchantProductsController;
import com.bestcommerce.controller.RegistrationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SignupServiceApplicationTests {

  @Autowired private HomeController homeController;

  @Autowired private LoginController loginController;

  @Autowired private RegistrationController registrationController;

  @Autowired private MerchantProductsController merchantProductsController;

  @Test
  @DisplayName("Test if controllers loading")
  void contextLoads() {
    assertAll(
        () -> assertThat(homeController).isNotNull(),
        () -> assertThat(loginController).isNotNull(),
        () -> assertThat(registrationController).isNotNull(),
        () -> assertThat(merchantProductsController).isNotNull());
  }
}
