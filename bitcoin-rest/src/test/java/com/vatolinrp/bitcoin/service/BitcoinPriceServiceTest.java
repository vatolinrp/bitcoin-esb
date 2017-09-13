package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.dao.PriceDAO;
import com.vatolinrp.bitcoin.model.BitcoinPriceValues;
import com.vatolinrp.bitcoin.model.blockchain.BitcoinPrice;
import com.vatolinrp.bitcoin.model.blockchain.Currency;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BitcoinPriceServiceTest
{
  @Test
  public void getBitcoinPricesSuccessTest()
  {
    BitcoinPriceService bitcoinPriceService = new BitcoinPriceService();
    PriceDAO priceDAO = Mockito.mock( PriceDAO.class );
    ReflectionTestUtils.setField( bitcoinPriceService, "priceDAO", priceDAO );
    Currency currencyUsd = new Currency();
    currencyUsd.setLast( 1.1 );
    Currency currencyCny = new Currency();
    currencyCny.setLast( 2.2 );
    Currency currencyEur = new Currency();
    currencyEur.setLast( 3.3 );
    BitcoinPrice bitcoinPrice = new BitcoinPrice();
    bitcoinPrice.setUsd( currencyUsd );
    bitcoinPrice.setCny( currencyCny );
    bitcoinPrice.setEur( currencyEur );
    Mockito.when( priceDAO.getPrice() ).thenReturn( bitcoinPrice );
    BitcoinPriceValues bitcoinPriceValues = bitcoinPriceService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPriceValues );
    Assert.assertEquals( bitcoinPriceValues.getUsd(), 1.1 );
    Assert.assertEquals( bitcoinPriceValues.getCny(), 2.2 );
    Assert.assertEquals( bitcoinPriceValues.getEur(), 3.3 );

  }

  @Test
  public void getBitcoinPricesPartialSuccessTest()
  {
    BitcoinPriceService bitcoinService = new BitcoinPriceService();
    PriceDAO priceDAO = Mockito.mock( PriceDAO.class );
    ReflectionTestUtils.setField( bitcoinService, "priceDAO", priceDAO );
    BitcoinPrice bitcoinPrice = new BitcoinPrice();
    Mockito.when( priceDAO.getPrice() ).thenReturn( bitcoinPrice );


    BitcoinPriceValues bitcoinPriceValues = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPriceValues );
    Assert.assertNull( bitcoinPriceValues.getUsd() );
    Assert.assertNull( bitcoinPriceValues.getCny() );
    Assert.assertNull( bitcoinPriceValues.getEur() );

    Currency currency = new Currency();
    currency.setLast( 1.1 );
    bitcoinPrice.setUsd( currency );
    bitcoinPriceValues = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPriceValues );
    Assert.assertNull( bitcoinPriceValues.getEur() );
    Assert.assertNull( bitcoinPriceValues.getCny() );
    Assert.assertEquals( bitcoinPriceValues.getUsd(), 1.1 );

    currency = new Currency();
    currency.setLast( 2.2 );
    bitcoinPrice.setCny( currency );
    bitcoinPriceValues = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPriceValues );
    Assert.assertNull( bitcoinPriceValues.getEur() );
    Assert.assertEquals( bitcoinPriceValues.getUsd(), 1.1 );
    Assert.assertEquals( bitcoinPriceValues.getCny(), 2.2 );

    currency = new Currency();
    currency.setLast( 3.3 );
    bitcoinPrice.setEur( currency );
    bitcoinPriceValues = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPriceValues );
    Assert.assertEquals( bitcoinPriceValues.getUsd(), 1.1 );
    Assert.assertEquals( bitcoinPriceValues.getCny(), 2.2 );
    Assert.assertEquals( bitcoinPriceValues.getEur(), 3.3 );
  }
}
