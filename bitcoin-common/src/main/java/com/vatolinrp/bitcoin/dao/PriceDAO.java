package com.vatolinrp.bitcoin.dao;

import com.vatolinrp.bitcoin.model.blockchain.BitcoinPrice;

public interface PriceDAO
{
  /**
   * Gets prices for each currency for one bitcoin
   */
  BitcoinPrice getPrice();
}
