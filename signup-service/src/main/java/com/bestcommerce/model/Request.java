package com.bestcommerce.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request implements Serializable {
  private static final Long serialVersionUID = 12345L;
  private Long merchantId;
  private Integer pageNo;
  private Integer pageSize;
  private String sortBy;

  public static Request getRequest(Long merchantId, Integer pageNo, Integer pageSize, String sortBy) {
    return new Request(merchantId, pageNo, pageSize, sortBy);
  }
}
