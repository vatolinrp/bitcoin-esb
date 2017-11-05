package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.dao.PriceDAO;
import com.vatolinrp.bitcoin.model.BitcoinPriceValues;
import com.vatolinrp.bitcoin.model.CurrencyCodeEnum;
import com.vatolinrp.bitcoin.model.Ping;
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

public class BitcoinPriceServiceTest
{
  @Mock
  private PriceDAO priceDAO;

  @InjectMocks
  private BitcoinPriceService bitcoinPriceService;

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
    final BitcoinPriceValues bitcoinPriceValues = bitcoinPriceService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPriceValues );
    Assert.assertEquals( bitcoinPriceValues.getPrices().get( CurrencyCodeEnum.USD.name() ), 1.1 );
    Assert.assertEquals( bitcoinPriceValues.getPrices().get( CurrencyCodeEnum.CNY.name() ), 2.2 );
    Assert.assertEquals( bitcoinPriceValues.getPrices().get( CurrencyCodeEnum.EUR.name() ), 3.3 );
    Mockito.verify( priceDAO, Mockito.only() ).getPrice();
  }

  @Test
  public void getBitcoinPricesWhenEmptyMapReturned()
  {
    final Map<CurrencyCodeEnum, Double> bitcoinPriceValuesMap = new EnumMap<>( CurrencyCodeEnum.class );
    Mockito.when( priceDAO.getPrice() ).thenReturn( bitcoinPriceValuesMap );
    final BitcoinPriceValues bitcoinPriceValues = bitcoinPriceService.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPriceValues );
    Assert.assertTrue( bitcoinPriceValues.getPrices().isEmpty() );
    Mockito.verify( priceDAO, Mockito.only() ).getPrice();
  }

  @Test
  public void checkPing()
  {
    final Ping ping = bitcoinPriceService.ping();
    Assert.assertNotNull( ping );
    Assert.assertNotNull( ping.getStatus() );
    Assert.assertEquals( ping.getStatus(), "Active" );
  }
}
