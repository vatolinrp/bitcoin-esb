package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.dao.PriceDAO;
import com.vatolinrp.bitcoin.generated.service.BitcoinPricesResponse;
import com.vatolinrp.bitcoin.generated.service.BitcoinServiceInterface;
import com.vatolinrp.bitcoin.model.blockchain.BitcoinPrice;
import com.vatolinrp.bitcoin.model.blockchain.Currency;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BitcoinServiceImplTest
{
  @Test
  public void getBitcoinPricesSuccessfully()
  {
    final BitcoinServiceInterface bitcoinService = new BitcoinServiceImpl();
    final PriceDAO priceDAO = Mockito.mock( PriceDAO.class );
    ReflectionTestUtils.setField( bitcoinService, "priceDAO", priceDAO );
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
    final BitcoinPricesResponse bitcoinPricesResponse = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertEquals( bitcoinPricesResponse.getUsd(), 1.1 );
    Assert.assertEquals( bitcoinPricesResponse.getCny(), 2.2 );
    Assert.assertEquals( bitcoinPricesResponse.getEur(), 3.3 );
    Mockito.verify( priceDAO ).getPrice();
  }

  @Test
  public void getBitcoinPricesPartialSuccessful()
  {
    final BitcoinServiceImpl bitcoinService = new BitcoinServiceImpl();
    final PriceDAO priceDAO = Mockito.mock( PriceDAO.class );
    ReflectionTestUtils.setField( bitcoinService, "priceDAO", priceDAO );
    final BitcoinPrice bitcoinPrice = new BitcoinPrice();
    Mockito.when( priceDAO.getPrice() ).thenReturn( bitcoinPrice );


    BitcoinPricesResponse bitcoinPricesResponse = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertNull( bitcoinPricesResponse.getUsd() );
    Assert.assertNull( bitcoinPricesResponse.getCny() );
    Assert.assertNull( bitcoinPricesResponse.getEur() );
    Mockito.verify( priceDAO, Mockito.times( 1 ) ).getPrice();

    Currency currency = new Currency();
    currency.setLast( 1.1 );
    bitcoinPrice.setUsd( currency );
    bitcoinPricesResponse = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertNull( bitcoinPricesResponse.getEur() );
    Assert.assertNull( bitcoinPricesResponse.getCny() );
    Assert.assertEquals( bitcoinPricesResponse.getUsd(), 1.1 );
    Mockito.verify( priceDAO, Mockito.times( 2 ) ).getPrice();

    currency = new Currency();
    currency.setLast( 2.2 );
    bitcoinPrice.setCny( currency );
    bitcoinPricesResponse = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertNull( bitcoinPricesResponse.getEur() );
    Assert.assertEquals( bitcoinPricesResponse.getUsd(), 1.1 );
    Assert.assertEquals( bitcoinPricesResponse.getCny(), 2.2 );
    Mockito.verify( priceDAO, Mockito.times( 3 ) ).getPrice();

    currency = new Currency();
    currency.setLast( 3.3 );
    bitcoinPrice.setEur( currency );
    bitcoinPricesResponse = bitcoinService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertEquals( bitcoinPricesResponse.getUsd(), 1.1 );
    Assert.assertEquals( bitcoinPricesResponse.getCny(), 2.2 );
    Assert.assertEquals( bitcoinPricesResponse.getEur(), 3.3 );
    Mockito.verify( priceDAO, Mockito.times( 4 ) ).getPrice();
  }
}
