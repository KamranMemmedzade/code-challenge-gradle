package com.bestcommerce.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product implements Serializable {

  private static final long serialVersionUID = 1234567L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @Enumerated(EnumType.STRING)
  @JsonProperty("productCategory")
  private ProductCategory productCategory;

  @JsonProperty("unitPrice")
  private int unitPrice;

  @JsonProperty("inventory")
  private int inventory;

  @Enumerated(EnumType.STRING)
  @JsonProperty("paymentOptions")
  private PaymentOptions paymentOptions;

  @Enumerated(EnumType.STRING)
  @JsonProperty("deliveryOptions")
  private DeliveryOptions deliveryOptions;

  @JsonProperty("merchantId")
  private Long merchantId;

  public Product(
      String name,
      String description,
      ProductCategory productCategory,
      int unitPrice,
      int inventory,
      PaymentOptions paymentOptions,
      DeliveryOptions deliveryOptions) {
    this.name = name;
    this.description = description;
    this.productCategory = productCategory;
    this.unitPrice = unitPrice;
    this.inventory = inventory;
    this.paymentOptions = paymentOptions;
    this.deliveryOptions = deliveryOptions;
  }
}
