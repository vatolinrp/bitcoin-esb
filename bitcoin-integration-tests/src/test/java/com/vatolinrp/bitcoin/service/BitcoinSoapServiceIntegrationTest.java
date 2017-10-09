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
  public void test()
  {
    final BitcoinPricesResponse bitcoinPricesResponse = bitcoinServiceInterface.getBitcoinPrices();
    Assert.assertNotNull( bitcoinPricesResponse );
    Assert.assertNotNull( bitcoinPricesResponse.getUsd() );
    Assert.assertNotNull( bitcoinPricesResponse.getCny() );
    Assert.assertNotNull( bitcoinPricesResponse.getEur() );
  }
}