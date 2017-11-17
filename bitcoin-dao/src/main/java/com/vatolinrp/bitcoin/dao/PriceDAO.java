package com.vatolinrp.bitcoin.dao;

import com.vatolinrp.bitcoin.model.CurrencyCodeEnum;

import java.util.Map;

public interface PriceDAO
{
  /**
   * Gets prices for each currency for one bitcoin
   */
  Map<CurrencyCodeEnum, Double> getPrice();
}
