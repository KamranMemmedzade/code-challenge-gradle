package com.bestcommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.bestcommerce.model.Request;
import com.bestcommerce.model.product.Product;
import com.bestcommerce.model.product.ProductWrapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;

@ExtendWith(MockitoExtension.class)
class RabbitMessageSendingServiceTest {

  private static final String QUEUE_NAME = "signup";
  private static final String ID_QUEUE_NAME = "products";
  @InjectMocks
  RabbitMessageSendingService rabbitMessageSendingService;
  @Mock RabbitTemplate rabbitTemplate;
  Product product;

  @Test
  @DisplayName("Test if saving product to db is working (via queue)")
  void sendProduct() {
    product = new Product();
    product.setName("Logitech");

    when(rabbitTemplate.convertSendAndReceiveAsType(
            QUEUE_NAME, product, ParameterizedTypeReference.forType(Product.class)))
        .thenReturn(product);

    Product sentProduct = rabbitMessageSendingService.sendProduct(product);

    assertNotNull(sentProduct);

    assertEquals("Logitech", sentProduct.getName());
  }

  @Test
  @DisplayName("Test if getting merchant product from db is working (via queue)")
  void sendId() {

    Request request = new Request(2L, 0, 3, "inventory");
    List<Product> mockList = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      product = new Product();
      product.setName("Logitech " + i);
      mockList.add(product);
    }

    ProductWrapper productWrapper = ProductWrapper.getProductWrapper(mockList);

    //productWrapper.setProductList(mockList);
    when(rabbitTemplate.convertSendAndReceiveAsType(
            ID_QUEUE_NAME, request, ParameterizedTypeReference.forType(ProductWrapper.class)))
        .thenReturn(productWrapper);

    ProductWrapper testPw = rabbitMessageSendingService.sendId(2L, 0, 3, "inventory");
    assertNotNull(testPw);

    assertTrue(testPw.getProductList().size() > 0);
    assertEquals(3, testPw.getProductList().size());
    assertEquals("Logitech 2", testPw.getProductList().get(2).getName());
  }
}
