package com.bestcommerce.util;

import com.bestcommerce.model.Merchant;
import com.bestcommerce.model.MerchantDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoConverter {

  private final ModelMapper modelMapper;

  public Merchant convertDtoToEntity(MerchantDto merchantDto) {

    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

    return modelMapper.map(merchantDto, Merchant.class);
  }

  public MerchantDto convertEntityToDto(Merchant merchant) {
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

    return modelMapper.map(merchant, MerchantDto.class);
  }
}
