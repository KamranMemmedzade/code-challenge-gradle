package com.bestcommerce.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Request implements Serializable {
  private static final Long serialVersionUID = 12345L;
  private Long merchantId;
  private Integer pageNo;
  private Integer pageSize;
  private String sortBy;
}
