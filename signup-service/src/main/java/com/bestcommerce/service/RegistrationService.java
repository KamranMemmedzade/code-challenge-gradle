package com.bestcommerce.service;

import com.bestcommerce.exception.EmailAlreadyExistsException;
import com.bestcommerce.model.Merchant;
import com.bestcommerce.model.MerchantDto;
import com.bestcommerce.repository.MerchantRepository;
import com.bestcommerce.util.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

  private final MerchantRepository merchantRepository;

  private final PasswordEncoder passwordEncoder;

  private final DtoConverter dtoConverter;

  public Merchant signUp(MerchantDto merchantDto) {

    if (!merchantRepository.findAll().isEmpty()) {
      if (merchantRepository.findByEmail(merchantDto.getEmail()).isPresent()) {
        throw new EmailAlreadyExistsException("User already exists");
      }
    }

    Merchant merchant = dtoConverter.convertDtoToEntity(merchantDto);

    merchant.setPhoneNumber(merchantDto.getPhoneNumber().replaceAll("[\\D]", ""));

    merchant.setPassword(passwordEncoder.encode(merchant.getPassword()));

    merchantRepository.save(merchant);

    return merchant;
  }
}
