package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.dao.PriceDAO;
import com.vatolinrp.bitcoin.generated.service.BitcoinPricesResponse;
import com.vatolinrp.bitcoin.model.CurrencyCodeEnum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.EnumMap;
import java.util.Map;

public class BitcoinServiceImplTest
{
  @Mock
  private PriceDAO priceDAO;

  @InjectMocks
  private BitcoinServiceImpl bitcoinServiceInterface;

  @BeforeClass
  private void setup()
  {
    MockitoAnnotations.initMocks( this );
  }

  @AfterMethod
  private void reset()
  {
    Mockito.reset( priceDAO );
  }

  @Test
  public void getBitcoinPricesSuccessfully()
  {
    final Map<CurrencyCodeEnum, Double> bitcoinPriceValuesMap = new EnumMap<>( CurrencyCodeEnum.class );
    bitcoinPriceValuesMap.put( CurrencyCodeEnum.USD, 1.1 );
    bitcoinPriceValuesMap.put( CurrencyCodeEnum.CNY, 2.2 );
    bitcoinPriceValuesMap.put( CurrencyCodeEnum.EUR, 3.3 );
    Mockito.when( priceDAO.getPrice() ).thenReturn( bitcoinPriceValuesMap );
    final BitcoinPricesResponse bitcoinPricesResponse = bitcoinServiceInterface.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertEquals( bitcoinPricesResponse.getPriceResult().get( 0 ).getCurrencyCode(), CurrencyCodeEnum.USD.name() );
    Assert.assertEquals( bitcoinPricesResponse.getPriceResult().get( 0 ).getValue(), 1.1 );
    Assert.assertEquals( bitcoinPricesResponse.getPriceResult().get( 1 ).getCurrencyCode(), CurrencyCodeEnum.CNY.name() );
    Assert.assertEquals( bitcoinPricesResponse.getPriceResult().get( 1 ).getValue(), 2.2 );
    Assert.assertEquals( bitcoinPricesResponse.getPriceResult().get( 2 ).getCurrencyCode(), CurrencyCodeEnum.EUR.name() );
    Assert.assertEquals( bitcoinPricesResponse.getPriceResult().get( 2 ).getValue(), 3.3 );
    Mockito.verify( priceDAO, Mockito.only() ).getPrice();
  }

  @Test
  public void getBitcoinPricesPartialSuccessful()
  {
    final Map<CurrencyCodeEnum, Double> bitcoinPriceValuesMap = new EnumMap<>( CurrencyCodeEnum.class );
    Mockito.when( priceDAO.getPrice() ).thenReturn( bitcoinPriceValuesMap );
    final BitcoinPricesResponse bitcoinPricesResponse = bitcoinServiceInterface.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertTrue( bitcoinPricesResponse.getPriceResult().isEmpty() );
    Mockito.verify( priceDAO, Mockito.only() ).getPrice();
  }
}
