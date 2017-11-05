package com.vatolinrp.bitcoin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

@ApiModel( value = "bitcoin price values", description = "bitcoin prices in different currencies" )
public class BitcoinPriceValues
{
  @ApiModelProperty( "bitcoin prices, where key is a currency code" )
  private Map<String, Double> prices;

  public Map<String, Double> getPrices()
  {
    if( prices == null ) {
      prices = new HashMap<>();
    }
    return prices;
  }
}
