package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.dao.PriceDAO;
import com.vatolinrp.bitcoin.generated.service.BitcoinPricesResponse;
import com.vatolinrp.bitcoin.generated.service.BitcoinServiceInterface;
import com.vatolinrp.bitcoin.model.BitcoinPrice;
import com.vatolinrp.bitcoin.model.Currency;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BitcoinServiceImplTest
{
  @Test
  public void getBitcoinPricesSuccessTest()
  {
    BitcoinServiceInterface bitcoinService = new BitcoinServiceImpl();
    PriceDAO priceDAO = Mockito.mock( PriceDAO.class );
    ReflectionTestUtils.setField( bitcoinService, "priceDAO", priceDAO );
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
    BitcoinPricesResponse bitcoinPricesResponse = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertNotNull( bitcoinPricesResponse.getUsd() );
    Assert.assertNotNull( bitcoinPricesResponse.getCny() );
    Assert.assertNotNull( bitcoinPricesResponse.getEur() );
    Assert.assertEquals( bitcoinPricesResponse.getUsd().getLast(), 1.1 );
    Assert.assertEquals( bitcoinPricesResponse.getCny().getLast(), 2.2 );
    Assert.assertEquals( bitcoinPricesResponse.getEur().getLast(), 3.3 );
  }

  @Test
  public void getBitcoinPricesPartialSuccessTest()
  {
    BitcoinServiceImpl bitcoinService = new BitcoinServiceImpl();
    PriceDAO priceDAO = Mockito.mock( PriceDAO.class );
    ReflectionTestUtils.setField( bitcoinService, "priceDAO", priceDAO );
    BitcoinPrice bitcoinPrice = new BitcoinPrice();
    Mockito.when( priceDAO.getPrice() ).thenReturn( bitcoinPrice );


    BitcoinPricesResponse bitcoinPricesResponse = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertNull( bitcoinPricesResponse.getUsd() );
    Assert.assertNull( bitcoinPricesResponse.getCny() );
    Assert.assertNull( bitcoinPricesResponse.getEur() );

    Currency currency = new Currency();
    currency.setLast( 1.1 );
    bitcoinPrice.setUsd( currency );
    bitcoinPricesResponse = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertNotNull( bitcoinPricesResponse.getUsd() );
    Assert.assertNull( bitcoinPricesResponse.getEur() );
    Assert.assertNull( bitcoinPricesResponse.getCny() );
    Assert.assertEquals( bitcoinPricesResponse.getUsd().getLast(), 1.1 );

    currency = new Currency();
    currency.setLast( 2.2 );
    bitcoinPrice.setCny( currency );
    bitcoinPricesResponse = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertNotNull( bitcoinPricesResponse.getUsd() );
    Assert.assertNull( bitcoinPricesResponse.getEur() );
    Assert.assertNotNull( bitcoinPricesResponse.getCny() );
    Assert.assertEquals( bitcoinPricesResponse.getUsd().getLast(), 1.1 );
    Assert.assertEquals( bitcoinPricesResponse.getCny().getLast(), 2.2 );

    currency = new Currency();
    currency.setLast( 3.3 );
    bitcoinPrice.setEur( currency );
    bitcoinPricesResponse = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertNotNull( bitcoinPricesResponse.getUsd() );
    Assert.assertNotNull( bitcoinPricesResponse.getEur() );
    Assert.assertNotNull( bitcoinPricesResponse.getCny() );
    Assert.assertEquals( bitcoinPricesResponse.getUsd().getLast(), 1.1 );
    Assert.assertEquals( bitcoinPricesResponse.getCny().getLast(), 2.2 );
    Assert.assertEquals( bitcoinPricesResponse.getEur().getLast(), 3.3 );

  }
}
