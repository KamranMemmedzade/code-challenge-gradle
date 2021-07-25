package com.bestcommerce.service;

import com.bestcommerce.model.Merchant;
import com.bestcommerce.repository.MerchantRepository;
import com.bestcommerce.security.MerchantPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantDetailService implements UserDetailsService {

  private final MerchantRepository merchantRepository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

    Merchant merchant =
        merchantRepository
            .findByName(s)
            .orElseThrow(
                () -> new UsernameNotFoundException(String.format("%s user not found", s)));

    return MerchantPrincipal.getMerchantPrincipal(merchant);
  }
}
