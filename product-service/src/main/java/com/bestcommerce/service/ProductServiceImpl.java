package com.bestcommerce.service;

import com.bestcommerce.model.Product;
import com.bestcommerce.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository repository;

  public List<Product> getAllProducts(
      Long merchantId, Integer pageNo, Integer pageSize, String sortBy) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

    Page<Product> pagedResult =
        repository
            .findProductsByMerchantId(merchantId, paging)
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        String.format("There is no product belong to %s ", merchantId.toString())));

    return pagedResult.getContent();
  }

  @Transactional
  public Product saveProduct(Product product) {

    long id = product.getMerchantId();
    String name = product.getName();
    int inventory = product.getInventory();

    if (repository.existsByMerchantIdAndName(id, name)) {
      repository.setProductInfoById(inventory, id, name);
    } else {
      repository.save(product);
    }
    return product;
  }
}
