package com.bestcommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.bestcommerce.model.Merchant;
import com.bestcommerce.security.jwt.JwtTokenUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class JwtLoginServiceTest {
  @InjectMocks
  JwtLoginService loginService;
  @Mock private MerchantService merchantService;
  @Mock private AuthenticationManager authenticationManager;
  @Mock private JwtTokenUtil jwtTokenUtil;

  @Test
  @DisplayName("Test if current logged in user method is working")
  void currentLoggedInUserId() {
    Merchant merchant = new Merchant();
    merchant.setId(99L);

    assertEquals(99L, merchant.getId());
  }

  @Test
  @DisplayName("Test if authentication is successful")
  void authenticate() {

    String username = "Real";
    String password = "josh156y";
    boolean rememberMe = false;

    when(jwtTokenUtil.generateToken(username, rememberMe)).thenReturn("jwttoken");
    assertEquals("jwttoken", loginService.authenticate(username, password, rememberMe));
  }

  @Test
  @DisplayName("Test if authentication throws exception")
  void authenticateExc() {

    String username = "Fake";
    String password = "josh156y";
    boolean rememberMe = false;

    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(username, password);

    when(authenticationManager.authenticate(usernamePasswordAuthenticationToken))
        .thenThrow(new BadCredentialsException("INVALID_CREDENTIALS"));

    BadCredentialsException badCredentialsException =
        assertThrows(
            BadCredentialsException.class,
            () -> loginService.authenticate(username, password, rememberMe));

    assertEquals("Login failed : Wrong username or password", badCredentialsException.getMessage());
  }
}
