package com.bestcommerce.security;

import com.bestcommerce.model.Merchant;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MerchantPrincipal implements UserDetails {

  private final Merchant merchant;

  public MerchantPrincipal(Merchant merchant) {
    this.merchant = merchant;
  }

  public static MerchantPrincipal getMerchantPrincipal(Merchant merchant) {
    return new MerchantPrincipal(merchant);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority simpleGrantedAuthority =
        new SimpleGrantedAuthority(merchant.getMerchantType().name());

    return Collections.singletonList(simpleGrantedAuthority);
  }

  @Override
  public String getPassword() {
    return merchant.getPassword();
  }

  @Override
  public String getUsername() {
    return merchant.getName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
