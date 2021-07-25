package com.bestcommerce.repository;

import com.bestcommerce.model.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

  Optional<Page<Product>> findProductsByMerchantId(Long id, Pageable pageable);

  Boolean existsByMerchantIdAndName(long id, String productName);

  @Modifying
  @Query("UPDATE Product p set p.inventory = ?1 WHERE p.merchantId = ?2 and p.name=?3")
  void setProductInfoById(int inventory, Long merchantId, String name);
}
