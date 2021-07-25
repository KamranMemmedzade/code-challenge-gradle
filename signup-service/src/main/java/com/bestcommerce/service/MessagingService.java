package com.bestcommerce.service;

import com.bestcommerce.model.product.Product;
import com.bestcommerce.model.product.ProductWrapper;

public interface MessagingService {

  Product sendProduct(Product product);

  ProductWrapper sendId(Long merchantId, int pageNo, int pageSize, String sortBy);
}
