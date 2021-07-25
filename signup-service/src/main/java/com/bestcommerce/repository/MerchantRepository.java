package com.bestcommerce.repository;

import com.bestcommerce.model.Merchant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
  Optional<Merchant> findByName(String name);

  Optional<Merchant> findByEmail(String email);
}
