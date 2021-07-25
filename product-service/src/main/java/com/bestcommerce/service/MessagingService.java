package com.bestcommerce.service;

import com.bestcommerce.model.Product;
import com.bestcommerce.model.ProductWrapper;
import com.bestcommerce.model.Request;

public interface MessagingService {

  Product listenForProduct(Product product);

  ProductWrapper listenForId(Request request);
}
