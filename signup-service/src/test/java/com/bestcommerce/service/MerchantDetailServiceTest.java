package com.bestcommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.bestcommerce.model.Merchant;
import com.bestcommerce.repository.MerchantRepository;
import com.bestcommerce.security.MerchantPrincipal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class MerchantDetailServiceTest {

  final String realName = "Kamran";
  final String fakeName = "Joshua";
  @Mock MerchantRepository merchantRepository;
  @InjectMocks MerchantDetailService merchantDetailService;

  @Test
  @DisplayName("Test loading user when exists in db")
  void loadUserByUsername() {

    Merchant merchant = new Merchant();

    merchant.setName(realName);

    when(merchantRepository.findByName(realName)).thenReturn(Optional.of(merchant));

    MerchantPrincipal merchantPrincipal =
        (MerchantPrincipal) merchantDetailService.loadUserByUsername(realName);

    assertNotNull(merchantPrincipal);

    assertEquals(realName, merchantPrincipal.getUsername());
  }

  @Test
  @DisplayName("Test loading user when not exists in db")
  void loadUserByUsernameException() {

    UsernameNotFoundException exception =
        assertThrows(
            UsernameNotFoundException.class,
            () -> merchantDetailService.loadUserByUsername(fakeName));

    assertEquals(String.format("%s user not found", fakeName), exception.getMessage());
  }
}
