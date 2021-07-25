package com.bestcommerce.model.product;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductWrapper {

  private List<Product> productList;

  public static ProductWrapper getProductWrapper() {
    return new ProductWrapper();
  }

  public static ProductWrapper getProductWrapper(List<Product> productList) {
    return new ProductWrapper(productList);
  }
}
