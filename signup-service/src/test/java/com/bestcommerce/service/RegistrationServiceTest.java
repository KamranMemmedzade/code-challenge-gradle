package com.bestcommerce.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.bestcommerce.exception.EmailAlreadyExistsException;
import com.bestcommerce.model.Merchant;
import com.bestcommerce.model.MerchantDto;
import com.bestcommerce.model.MerchantType;
import com.bestcommerce.repository.MerchantRepository;
import com.bestcommerce.util.DtoConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

  @Mock MerchantRepository merchantRepository;

  @Mock PasswordEncoder passwordEncoder;

  @Mock
  DtoConverter dtoConverter;

  @InjectMocks RegistrationService registrationService;

  MerchantDto merchantDto;

  Merchant merchant;

  ModelMapper modelMapper;

  @BeforeEach
  void setUp() {
    passwordEncoder = new BCryptPasswordEncoder();
    modelMapper = new ModelMapper();
    registrationService =
        new RegistrationService(merchantRepository, passwordEncoder, dtoConverter);

    merchantDto =
        new MerchantDto(
            "DtoName",
            "k981mop0",
            "DtoOwnerName",
            "dtoAddress",
            "+99455 #16279128!",
            "dtoEmail@yqhoo.com",
            MerchantType.LOCAL);

    merchant = modelMapper.map(merchantDto, Merchant.class);
  }

  @Test
  @DisplayName("Test if saving user to database is successful")
  void signUp() {

    when(merchantRepository.findAll()).thenReturn(List.of());
    when(dtoConverter.convertDtoToEntity(merchantDto)).thenReturn(merchant);

    Merchant foundMerchant = registrationService.signUp(merchantDto);

    assertAll(
        () -> assertNotNull(foundMerchant),
        () -> assertNotEquals(merchantDto.getPassword(), foundMerchant.getPassword()),
        () -> assertNotEquals(merchantDto.getPhoneNumber(), foundMerchant.getPhoneNumber()),
        () -> assertEquals(merchant.getPassword(), foundMerchant.getPassword()));
  }

  @Test
  @DisplayName("Test if user saving to db fails (user exist in db)")
  void signUpException() {

    when(merchantRepository.findAll()).thenReturn(List.of(merchant));

    when(merchantRepository.findByEmail(merchantDto.getEmail()))
        .thenThrow(new EmailAlreadyExistsException("User already exists"));

    EmailAlreadyExistsException exception =
        assertThrows(
            EmailAlreadyExistsException.class, () -> registrationService.signUp(merchantDto));

    assertNotNull(exception);

    assertEquals("User already exists", exception.getMessage());
  }
}
