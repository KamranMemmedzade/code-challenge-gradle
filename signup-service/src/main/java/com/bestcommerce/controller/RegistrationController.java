package com.bestcommerce.controller;

import com.bestcommerce.exception.FormValidationException;
import com.bestcommerce.model.Merchant;
import com.bestcommerce.model.MerchantDto;
import com.bestcommerce.response.RegistrationResponse;
import com.bestcommerce.service.RegistrationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class RegistrationController {

  private final RegistrationService registrationService;

  private final ModelMapper modelMapper;

  @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> register(
      @Valid @RequestBody MerchantDto merchantDto, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      throw new FormValidationException(bindingResult.getAllErrors().toString());
    }
    Merchant merchant = registrationService.signUp(merchantDto);

    RegistrationResponse registrationResponse =
        modelMapper.map(merchant, RegistrationResponse.class);

    return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
  }
}
