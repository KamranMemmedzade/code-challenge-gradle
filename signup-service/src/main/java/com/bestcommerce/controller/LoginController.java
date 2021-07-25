package com.bestcommerce.controller;

import com.bestcommerce.security.jwt.JwtRequest;
import com.bestcommerce.security.jwt.JwtResponse;
import com.bestcommerce.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<?> logIn(
      @RequestBody JwtRequest authenticationRequest,
      @RequestParam(defaultValue = "false") Boolean rememberMe) {

    String token =
        loginService.authenticate(
            authenticationRequest.getUsername(), authenticationRequest.getPassword(), rememberMe);
    return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(token));
  }
}
