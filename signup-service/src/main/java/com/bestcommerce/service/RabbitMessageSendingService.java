package com.bestcommerce.service;

import com.bestcommerce.model.Request;
import com.bestcommerce.model.product.Product;
import com.bestcommerce.model.product.ProductWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMessageSendingService implements MessagingService {

  private static final String QUEUE_NAME = "signup";
  private static final String ID_QUEUE_NAME = "products";

  private final RabbitTemplate rabbitTemplate;

  @Override
  public Product sendProduct(Product product) {
    return rabbitTemplate.convertSendAndReceiveAsType(
        QUEUE_NAME, product, ParameterizedTypeReference.forType(Product.class));
  }

  @Override
  public ProductWrapper sendId(Long merchantId, int pageNo, int pageSize, String sortBy) {
    Request request = Request.getRequest(merchantId, pageNo, pageSize, sortBy);

    return rabbitTemplate.convertSendAndReceiveAsType(
        ID_QUEUE_NAME, request, ParameterizedTypeReference.forType(ProductWrapper.class));
  }
}
