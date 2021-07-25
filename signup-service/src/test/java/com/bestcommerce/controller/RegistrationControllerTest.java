package com.bestcommerce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bestcommerce.model.Merchant;
import com.bestcommerce.model.MerchantDto;
import com.bestcommerce.model.MerchantType;
import com.bestcommerce.service.MerchantService;
import com.bestcommerce.service.RegistrationService;
import com.bestcommerce.util.DtoConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class RegistrationControllerTest {

  @MockBean RegistrationService registrationService;

  @MockBean MerchantService merchantService;

  @MockBean
  DtoConverter dtoConverter;

  @Autowired ModelMapper modelMapper;

  MerchantDto merchantDto;

  ValidatorFactory validatorFactory;

  Validator validator;

  @Autowired MockMvc mockMvc;
  @Autowired RegistrationController registrationController;
  Merchant merchant;
  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
    objectMapper = new ObjectMapper();

    merchantDto =
        new MerchantDto(
            "Adam", "kamran99", "adamam", "dubai", "00000000", "adam@mail.ru", MerchantType.LOCAL);

    merchant = modelMapper.map(merchantDto, Merchant.class);
  }

  @Test
  @DisplayName("Test if registration controller working properly")
  void testRegister() throws Exception {

    when(registrationService.signUp(merchantDto)).thenReturn(merchant);

    MvcResult mvcResult =
        mockMvc
            .perform(
                post("/register").content(req(merchantDto)).contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated())
            .andReturn();

    System.out.println(mvcResult.getResponse().getContentAsString());
  }

  @Test
  @DisplayName("Test if  name validation rule is working")
  void testNameValidation() {

    merchantDto.setName("");
    List<ConstraintViolation<MerchantDto>> list =
        new ArrayList<>(validator.validate(merchantDto));

    assertEquals("Name cannot be empty", list.get(0).getMessage());

    assertFalse(list.isEmpty());
  }

  @Test
  @DisplayName("Test if password length validation rule is working")
  void testPasswordValidation() {

    merchantDto.setPassword("kam");
    List<ConstraintViolation<MerchantDto>> list =
        new ArrayList<>(validator.validate(merchantDto));

    assertEquals(
        "Password should be alphanumeric and min 6 characters long", list.get(0).getMessage());

    assertFalse(list.isEmpty());
  }

  @Test
  @DisplayName("Test if email validation rule is working")
  void testEmailValidation() {

    merchantDto.setEmail("kam");

    List<ConstraintViolation<MerchantDto>> list =
        new ArrayList<>(validator.validate(merchantDto));

    assertFalse(list.isEmpty());

    assertEquals("Email should be in correct format", list.get(0).getMessage());
  }

  String req(Object o) throws JsonProcessingException {
    return objectMapper.writeValueAsString(o);
  }
}
