package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.generated.service.BitcoinPricesResponse;
import com.vatolinrp.bitcoin.generated.service.BitcoinServiceInterface;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;

@ContextConfiguration( locations = { "classpath:soap-client.xml" } )
public class BitcoinSoapServiceIntegrationTest extends AbstractTestNGSpringContextTests
{
  @Resource
  private BitcoinServiceInterface bitcoinServiceInterface;

  @Test
  public void checkSoapOperation()
  {
    final BitcoinPricesResponse bitcoinPricesResponse = bitcoinServiceInterface.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertEquals( bitcoinPricesResponse.getPriceResult().size(), 3 );
    Assert.assertNotNull( bitcoinPricesResponse.getPriceResult().get( 0 ).getCurrencyCode() );
    Assert.assertNotNull( bitcoinPricesResponse.getPriceResult().get( 0 ).getValue() );
    Assert.assertNotNull( bitcoinPricesResponse.getPriceResult().get( 1 ).getCurrencyCode() );
    Assert.assertNotNull( bitcoinPricesResponse.getPriceResult().get( 1 ).getValue() );
    Assert.assertNotNull( bitcoinPricesResponse.getPriceResult().get( 2 ).getCurrencyCode() );
    Assert.assertNotNull( bitcoinPricesResponse.getPriceResult().get( 2 ).getValue() );
  }
}
