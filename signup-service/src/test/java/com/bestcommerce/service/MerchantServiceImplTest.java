package com.bestcommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bestcommerce.model.Merchant;
import com.bestcommerce.repository.MerchantRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MerchantServiceImplTest {

  final String realName = "Kamran";
  final String fakeName = "Joshua";
  @Mock MerchantRepository merchantRepository;
  @InjectMocks MerchantServiceImpl merchantService;

  @Test
  @DisplayName("Test if user is found when real name is given")
  void findByValidName() {

    Merchant merchant = new Merchant();
    merchant.setName(realName);
    when(merchantRepository.findByName(realName)).thenReturn(Optional.of(merchant));

    Merchant foundMerchant = merchantService.findByName(realName);

    assertEquals(foundMerchant.getName(), realName);

    verify(merchantRepository, atMostOnce()).findByName(anyString());
  }

  @Test
  @DisplayName("Test if user is NOT found when fake name is given")
  void findByInvalidName() {

    assertThrows(IllegalStateException.class, () -> merchantService.findByName(fakeName));

    verify(merchantRepository, atMostOnce()).findByName(anyString());
  }
}
