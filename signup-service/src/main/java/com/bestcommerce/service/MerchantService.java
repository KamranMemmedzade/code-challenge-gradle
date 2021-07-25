package com.bestcommerce.service;

import com.bestcommerce.model.Merchant;

public interface MerchantService {

  Merchant findByName(String name);
}
