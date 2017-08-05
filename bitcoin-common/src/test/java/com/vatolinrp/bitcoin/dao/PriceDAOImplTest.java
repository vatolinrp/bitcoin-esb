package com.vatolinrp.bitcoin.dao;

import com.vatolinrp.bitcoin.model.BitcoinPrice;
import org.apache.commons.io.IOUtils;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;

public class PriceDAOImplTest
{
  @Test
  public void getPriceTest() throws Exception
  {
    InputStream inputStream = this.getClass().getResourceAsStream( "/bitcoinPriceResponse.json" );
    String externalResponse = IOUtils.toString( inputStream, "UTF-8" );
    PriceDAO priceDAO = new PriceDAOImpl();
    RestTemplate restTemplate = Mockito.mock( RestTemplate.class );
    ReflectionTestUtils.setField( priceDAO, "restTemplate", restTemplate );
    ReflectionTestUtils.setField( priceDAO, "bitcoinPriceURL", "bitcoinPriceURL" );
    Mockito.when( restTemplate.getForObject( "bitcoinPriceURL", String.class ) ).thenReturn( externalResponse );
    BitcoinPrice bitcoinPrice = priceDAO.getPrice();
    Assert.assertNotNull( bitcoinPrice );
    Assert.assertNotNull( bitcoinPrice.getUsd() );
    Assert.assertNotNull( bitcoinPrice.getEur() );
    Assert.assertNotNull( bitcoinPrice.getCny() );
    Assert.assertEquals( bitcoinPrice.getUsd().getLast(), 2778.93 );
    Assert.assertEquals( bitcoinPrice.getEur().getLast(), 2340.75 );
    Assert.assertEquals( bitcoinPrice.getCny().getLast(), 18641.62 );
  }
}
