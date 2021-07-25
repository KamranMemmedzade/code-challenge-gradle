package com.bestcommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bestcommerce.model.Product;
import com.bestcommerce.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock ProductRepository productRepository;

  @InjectMocks ProductServiceImpl productServiceImpl;

  Product product;
  @Autowired Page<Product> pagedResponse;

  @Autowired Pageable paging;

  @BeforeEach
  void setUp() {

    product = new Product();

    product.setMerchantId(2L);

    pagedResponse = new PageImpl(List.of(product));

    paging = PageRequest.of(0, 2, Sort.by("inventory"));
  }

  @Test
  @DisplayName("Test if get products successful with real merchant id")
  void testGetAllProducts() {

    when(productRepository.findProductsByMerchantId(2L, paging))
        .thenReturn(Optional.of(pagedResponse));

    List<Product> list = productServiceImpl.getAllProducts(2L, 0, 2, "inventory");

    assertTrue(list.size() > 0);

    verify(productRepository).findProductsByMerchantId(2L, paging);
  }

  @Test
  @DisplayName("Test if get products failed with fake merchant id")
  void testGetAllProductsException() {

    Long merchantId = 3L;
    when(productRepository.findProductsByMerchantId(merchantId, paging))
        .thenThrow(
            new IllegalStateException(
                String.format("There is no product belong to %s ", merchantId.toString())));

    Exception exception =
        assertThrows(
            IllegalStateException.class,
            () -> productServiceImpl.getAllProducts(3L, 0, 2, "inventory"));
    String expectedMessage = "There is no product belong to 3 ";
    String actualMessage = exception.getMessage();
    assertEquals(actualMessage, expectedMessage);

    verify(productRepository).findProductsByMerchantId(3L, paging);
  }

  @Test
  @DisplayName("Test if saving products successful")
  void testSaveProduct() {
    when(productServiceImpl.saveProduct(product)).thenReturn(product);

    Product product1 = productServiceImpl.saveProduct(product);

    assertNotNull(product1);

    verify(productRepository, times(1)).save(product);
  }
}
