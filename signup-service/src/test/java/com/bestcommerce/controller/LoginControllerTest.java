package com.bestcommerce.controller;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bestcommerce.security.jwt.JwtRequest;
import com.bestcommerce.security.jwt.JwtTokenUtil;
import com.bestcommerce.service.MerchantDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

  @MockBean AuthenticationManager authenticationManager;
  @MockBean JwtTokenUtil jwtTokenUtil;
  @MockBean MerchantDetailService merchantDetailService;

  @InjectMocks LoginController loginController;

  @Autowired MockMvc mockMvc;

  JwtRequest jwtRequest;

  @BeforeEach
  void setUp() {
    jwtRequest = new JwtRequest("kamra", "kamran99");
  }

  @Test
  @DisplayName("Test if login controller working properly")
  void logIn() throws Exception {

    when(jwtTokenUtil.generateToken(anyString(), anyBoolean()))
        .thenReturn("burada token ola bilerdi");

    String body =
        "{"
            + "\"username\""
            + ":"
            + "\""
            + jwtRequest.getUsername()
            + "\""
            + ","
            + "\"password\""
            + ":"
            + "\""
            + jwtRequest.getPassword()
            + "\""
            + "}";

    MvcResult mvcResult =
        mockMvc
            .perform(post("/login").content(body).contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn();

    String token = mvcResult.getResponse().getContentAsString();
    System.out.println(token);
  }
}
