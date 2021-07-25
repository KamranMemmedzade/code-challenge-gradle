package com.bestcommerce.service;

import com.bestcommerce.model.Product;
import java.util.List;

public interface ProductService {
  List<Product> getAllProducts(Long merchantId, Integer pageNo, Integer pageSize, String sortBy);

  Product saveProduct(Product product);
}
