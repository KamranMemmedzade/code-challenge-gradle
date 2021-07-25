package com.bestcommerce.service;

import com.bestcommerce.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtLoginService implements LoginService {

  private final MerchantService merchantService;

  private final AuthenticationManager authenticationManager;

  private final JwtTokenUtil jwtTokenUtil;

  @Override
  public Long currentLoggedInUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String currentUser = auth.getName();
    return merchantService.findByName(currentUser).getId();
  }

  public String authenticate(String username, String password, boolean rememberMe) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));

      return jwtTokenUtil.generateToken(username, rememberMe);
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("Login failed : Wrong username or password", e);
    }
  }
}
