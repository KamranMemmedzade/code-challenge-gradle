package com.bestcommerce.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bestcommerce.model.product.Product;
import com.bestcommerce.security.jwt.JwtTokenUtil;
import com.bestcommerce.service.LoginService;
import com.bestcommerce.service.MerchantService;
import com.bestcommerce.service.RabbitMessageSendingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.yml")
@WithMockUser(username = "ahmed", authorities = "ADMIN")
class MerchantProductsControllerTest {

  @Autowired MockMvc mockMvc;
  Product product;
  @Autowired JwtTokenUtil jwtTokenUtil;
  String token;
  @MockBean private MerchantService merchantService;
  @MockBean private RabbitMessageSendingService rabbitMessageSendingService;
  @MockBean private LoginService loginService;
  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    product = new Product();
    product.setName("Logitech");
  }

  @Test
  @DisplayName("Test if save user products is successful")
  void testSaveProductFail() throws Exception {

    when(loginService.currentLoggedInUserId()).thenReturn(2L);
    product.setInventory(9);
    mockMvc
        .perform(post("/saveproduct").content(req(product)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("Test if save products fails when inventory less than 5")
  void testSaveProductInventoryFail() throws Exception {

    product.setInventory(1);
    mockMvc
        .perform(post("/saveproduct").content(req(product)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Test if get user products is successful")
  void getMerchantProducts() throws Exception {

    when(loginService.currentLoggedInUserId()).thenReturn(2L);

    mockMvc
        .perform(
            get("/merchant/products").content(req(product)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Test if user fails when trying to sort by description")
  void getMerchantProductsException() throws Exception {

    when(loginService.currentLoggedInUserId()).thenReturn(2L);

    mockMvc
        .perform(
            get("/merchant/products")
                .param("sortBy", "description")
                .content(req(product))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  String req(Object o) throws JsonProcessingException {
    return objectMapper.writeValueAsString(o);
  }
}
