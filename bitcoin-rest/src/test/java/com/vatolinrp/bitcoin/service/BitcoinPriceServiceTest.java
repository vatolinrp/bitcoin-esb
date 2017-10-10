package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.dao.PriceDAO;
import com.vatolinrp.bitcoin.model.BitcoinPriceValues;
import com.vatolinrp.bitcoin.model.Ping;
import com.vatolinrp.bitcoin.model.blockchain.BitcoinPrice;
import com.vatolinrp.bitcoin.model.blockchain.Currency;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BitcoinPriceServiceTest
{
  @Test
  public void getBitcoinPricesSuccessfully()
  {
    final BitcoinPriceService bitcoinPriceService = new BitcoinPriceService();
    final PriceDAO priceDAO = Mockito.mock( PriceDAO.class );
    ReflectionTestUtils.setField( bitcoinPriceService, "priceDAO", priceDAO );
    final Currency currencyUsd = new Currency();
    currencyUsd.setLast( 1.1 );
    final Currency currencyCny = new Currency();
    currencyCny.setLast( 2.2 );
    final Currency currencyEur = new Currency();
    currencyEur.setLast( 3.3 );
    final BitcoinPrice bitcoinPrice = new BitcoinPrice();
    bitcoinPrice.setUsd( currencyUsd );
    bitcoinPrice.setCny( currencyCny );
    bitcoinPrice.setEur( currencyEur );
    Mockito.when( priceDAO.getPrice() ).thenReturn( bitcoinPrice );
    final BitcoinPriceValues bitcoinPriceValues = bitcoinPriceService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPriceValues );
    Assert.assertEquals( bitcoinPriceValues.getUsd(), 1.1 );
    Assert.assertEquals( bitcoinPriceValues.getCny(), 2.2 );
    Assert.assertEquals( bitcoinPriceValues.getEur(), 3.3 );
    Mockito.verify( priceDAO, Mockito.only() ).getPrice();
  }

  @Test
  public void checkPing()
  {
    final BitcoinPriceService bitcoinPriceService = new BitcoinPriceService();
    final Ping ping = bitcoinPriceService.ping();
    Assert.assertNotNull( ping );
    Assert.assertNotNull( ping.getStatus() );
    Assert.assertEquals( ping.getStatus(), "Active" );
  }

  @Test
  public void getBitcoinPricesPartialSuccessful()
  {
    final BitcoinPriceService bitcoinService = new BitcoinPriceService();
    final PriceDAO priceDAO = Mockito.mock( PriceDAO.class );
    ReflectionTestUtils.setField( bitcoinService, "priceDAO", priceDAO );
    final BitcoinPrice bitcoinPrice = new BitcoinPrice();
    Mockito.when( priceDAO.getPrice() ).thenReturn( bitcoinPrice );


    BitcoinPriceValues bitcoinPriceValues = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPriceValues );
    Assert.assertNull( bitcoinPriceValues.getUsd() );
    Assert.assertNull( bitcoinPriceValues.getCny() );
    Assert.assertNull( bitcoinPriceValues.getEur() );
    Mockito.verify( priceDAO, Mockito.times( 1 ) ).getPrice();

    Currency currency = new Currency();
    currency.setLast( 1.1 );
    bitcoinPrice.setUsd( currency );
    bitcoinPriceValues = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPriceValues );
    Assert.assertNull( bitcoinPriceValues.getEur() );
    Assert.assertNull( bitcoinPriceValues.getCny() );
    Assert.assertEquals( bitcoinPriceValues.getUsd(), 1.1 );
    Mockito.verify( priceDAO, Mockito.times( 2 ) ).getPrice();

    currency = new Currency();
    currency.setLast( 2.2 );
    bitcoinPrice.setCny( currency );
    bitcoinPriceValues = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPriceValues );
    Assert.assertNull( bitcoinPriceValues.getEur() );
    Assert.assertEquals( bitcoinPriceValues.getUsd(), 1.1 );
    Assert.assertEquals( bitcoinPriceValues.getCny(), 2.2 );
    Mockito.verify( priceDAO, Mockito.times( 3 ) ).getPrice();

    currency = new Currency();
    currency.setLast( 3.3 );
    bitcoinPrice.setEur( currency );
    bitcoinPriceValues = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPriceValues );
    Assert.assertEquals( bitcoinPriceValues.getUsd(), 1.1 );
    Assert.assertEquals( bitcoinPriceValues.getCny(), 2.2 );
    Assert.assertEquals( bitcoinPriceValues.getEur(), 3.3 );
    Mockito.verify( priceDAO, Mockito.times( 4 ) ).getPrice();
  }
}
