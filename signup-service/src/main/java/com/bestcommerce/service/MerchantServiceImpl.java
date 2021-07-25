package com.bestcommerce.service;

import com.bestcommerce.model.Merchant;
import com.bestcommerce.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

  private final MerchantRepository merchantRepository;

  @Override
  public Merchant findByName(String name) {

    return merchantRepository
        .findByName(name)
        .orElseThrow(() -> new IllegalStateException("Merchant not found"));
  }
}
