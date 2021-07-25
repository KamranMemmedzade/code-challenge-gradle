package com.bestcommerce.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bestcommerce.model.Merchant;
import com.bestcommerce.model.MerchantDto;
import com.bestcommerce.model.MerchantType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class DtoConverterTest {

  @Mock static ModelMapper modelMapper;

  @InjectMocks static DtoConverter dtoConverter;

  @BeforeAll
  static void setUp() {
    modelMapper = new ModelMapper();

    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

    dtoConverter = new DtoConverter(modelMapper);
  }

  @Test
  @DisplayName("Test converting DTO to ENTITY")
  void convertDtoToEntity() {

    MerchantDto merchantDto =
        new MerchantDto(
            "DtoName",
            "k981mop0",
            "DtoOwnerName",
            "dtoAddress",
            "+99455 #16279128!",
            "dtoEmail@yqhoo.com",
            MerchantType.LOCAL);

    Merchant convertedMerchant = dtoConverter.convertDtoToEntity(merchantDto);

    assertAll(
        () -> assertNotNull(convertedMerchant, "Converted merchant is null"),
        () ->
            assertEquals(merchantDto.getName(), convertedMerchant.getName(), "Name is not matched"),
        () ->
            assertEquals(
                merchantDto.getEmail(), convertedMerchant.getEmail(), "Email is not matched"),
        () ->
            assertEquals(
                merchantDto.getMerchantType(),
                convertedMerchant.getMerchantType(),
                "Type is not matched"),
        () ->
            assertEquals(
                merchantDto.getAddress(), convertedMerchant.getAddress(), "Address is not matched"),
        () ->
            assertEquals(
                merchantDto.getPassword(),
                convertedMerchant.getPassword(),
                "Password is not matched"),
        () ->
            assertEquals(
                merchantDto.getOwnerName(),
                convertedMerchant.getOwnerName(),
                "Owner name is not matched"),
        () ->
            assertEquals(
                merchantDto.getPhoneNumber(),
                convertedMerchant.getPhoneNumber(),
                "Phone is not matched"));
  }

  @Test
  @DisplayName("Test converting ENTITY to DTO")
  void convertEntityToDto() {
    Merchant merchant =
        new Merchant(
            "EntityName",
            "k981mop0",
            "entityEmail@yqhoo.com",
            MerchantType.LOCAL,
            "EntityOwnerName",
            "entityAddress",
            "+99455 #16279128!");

    MerchantDto convertedMerchantDto = dtoConverter.convertEntityToDto(merchant);

    assertAll(
        () -> assertNotNull(convertedMerchantDto, "Converted merchantDto is null"),
        () ->
            assertEquals(merchant.getName(), convertedMerchantDto.getName(), "Name is not matched"),
        () ->
            assertEquals(
                merchant.getEmail(), convertedMerchantDto.getEmail(), "Email is not matched"),
        () ->
            assertEquals(
                merchant.getMerchantType(),
                convertedMerchantDto.getMerchantType(),
                "Type is not matched"),
        () ->
            assertEquals(
                merchant.getAddress(), convertedMerchantDto.getAddress(), "Address is not matched"),
        () ->
            assertEquals(
                merchant.getPassword(),
                convertedMerchantDto.getPassword(),
                "Password is not matched"),
        () ->
            assertEquals(
                merchant.getOwnerName(),
                convertedMerchantDto.getOwnerName(),
                "Owner name is not matched"),
        () ->
            assertEquals(
                merchant.getPhoneNumber(),
                convertedMerchantDto.getPhoneNumber(),
                "Phone is not matched"));
  }
}
