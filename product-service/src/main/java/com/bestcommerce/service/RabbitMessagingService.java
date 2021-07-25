package com.bestcommerce.service;

import com.bestcommerce.model.Product;
import com.bestcommerce.model.ProductWrapper;
import com.bestcommerce.model.Request;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMessagingService implements MessagingService {

  private final ProductService service;

  @Override
  @RabbitListener(queues = {"signup"})
  public Product listenForProduct(Product product) {

    return service.saveProduct(product);
  }

  @Override
  @RabbitListener(queues = {"products"})
  public ProductWrapper listenForId(Request request) {

    List<Product> lst =
        service.getAllProducts(
            request.getMerchantId(),
            request.getPageNo(),
            request.getPageSize(),
            request.getSortBy());

    return ProductWrapper.getProductWrapper(lst);
  }
}
