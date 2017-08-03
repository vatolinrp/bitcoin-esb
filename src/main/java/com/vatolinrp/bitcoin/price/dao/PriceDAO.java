package com.vatolinrp.bitcoin.price.dao;

import com.vatolinrp.bitcoin.price.model.BitcoinPrice;

public interface PriceDAO
{
  /**
   * Gets prices for each currency for one bitcoin
   */
  BitcoinPrice getPrice();
}
