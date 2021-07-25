package com.bestcommerce.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantDto {

  @NotBlank(message = "Name cannot be empty")
  private String name;

  @Pattern(
      regexp = "^[a-zA-Z0-9]{6,20}",
      message = "Password should be alphanumeric and min 6 characters long")
  private String password;

  private String ownerName;

  private String address;

  private String phoneNumber;

  @NotBlank(message = "Email cannot be empty")
  @Email(message = "Email should be in correct format")
  private String email;

  @Enumerated(EnumType.STRING)
  private MerchantType merchantType;
}
