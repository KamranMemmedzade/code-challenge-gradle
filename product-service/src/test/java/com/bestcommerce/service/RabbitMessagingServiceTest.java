package com.bestcommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.bestcommerce.model.Product;
import com.bestcommerce.model.ProductWrapper;
import com.bestcommerce.model.Request;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RabbitMessagingServiceTest {

  @Mock ProductService service;

  @InjectMocks
  RabbitMessagingService rabbitMessagingService;

  Product product;

  @BeforeEach
  void setUp() {
    product = new Product();
    product.setName("Logitech");
  }

  @Test
  @DisplayName("Test if product is sent to queue")
  void saveListener() {
    when(service.saveProduct(product)).thenReturn(product);

    Product savedProduct = rabbitMessagingService.listenForProduct(product);

    assertNotNull(savedProduct);
    assertThat(savedProduct.getName().equals("Logitech"));
  }

  @Test
  @DisplayName("Test if merchant id is sent to queue")
  void idListener() {

    Request request = new Request(2L, 0, 3, "inventory");

    when(service.getAllProducts(
            request.getMerchantId(),
            request.getPageNo(),
            request.getPageSize(),
            request.getSortBy()))
        .thenReturn(List.of(product));

    ProductWrapper productWrapper = rabbitMessagingService.listenForId(request);

    assertNotNull(productWrapper);
    assertTrue(productWrapper.getProductList().size() != 0);
  }
}
