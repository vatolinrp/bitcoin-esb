package com.vatolinrp.bitcoin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( value = "Bitcoin price", description = "bitcoin price in different currencies" )
public class BitcoinPriceValues
{
  @ApiModelProperty( value = "Price of bitcoin in dollars" )
  private Double usd;

  @ApiModelProperty( value = "Price of bitcoin in yuan" )
  private Double cny;

  @ApiModelProperty( value = "Price of bitcoin in euro" )
  private Double eur;

  public Double getUsd()
  {
    return usd;
  }

  public void setUsd( final Double usd )
  {
    this.usd = usd;
  }

  public Double getCny()
  {
    return cny;
  }

  public void setCny( final Double cny )
  {
    this.cny = cny;
  }

  public Double getEur()
  {
    return eur;
  }

  public void setEur( final Double eur )
  {
    this.eur = eur;
  }
}
