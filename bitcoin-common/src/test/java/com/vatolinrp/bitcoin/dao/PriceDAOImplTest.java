package com.vatolinrp.bitcoin.dao;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatolinrp.bitcoin.model.blockchain.BitcoinPrice;
import org.apache.commons.io.IOUtils;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;

public class PriceDAOImplTest
{
  @Test
  public void getPrices() throws Exception
  {
    final InputStream inputStream = this.getClass().getResourceAsStream( "/bitcoinPriceResponse.json" );
    final String externalResponse = IOUtils.toString( inputStream, "UTF-8" );
    final PriceDAO priceDAO = new PriceDAOImpl();
    final RestTemplate restTemplate = Mockito.mock( RestTemplate.class );
    ReflectionTestUtils.setField( priceDAO, "restTemplate", restTemplate );
    ReflectionTestUtils.setField( priceDAO, "bitcoinPriceURL", "bitcoinPriceURL" );
    Mockito.when( restTemplate.getForObject( "bitcoinPriceURL", String.class ) ).thenReturn( externalResponse );
    final BitcoinPrice bitcoinPrice = priceDAO.getPrice();
    Assert.assertNotNull( bitcoinPrice );
    Assert.assertNotNull( bitcoinPrice.getUsd() );
    Assert.assertNotNull( bitcoinPrice.getEur() );
    Assert.assertNotNull( bitcoinPrice.getCny() );
    Assert.assertEquals( bitcoinPrice.getUsd().getLast(), 2778.93 );
    Assert.assertEquals( bitcoinPrice.getEur().getLast(), 2340.75 );
    Assert.assertEquals( bitcoinPrice.getCny().getLast(), 18641.62 );
    Mockito.verify( restTemplate, Mockito.only() ).getForObject( "bitcoinPriceURL", String.class );
  }

  @Test
  public void checkWhenExceptionFromRestTemplateCame() throws Exception
  {
    final PriceDAO priceDAO = new PriceDAOImpl();
    final RestTemplate restTemplate = Mockito.mock( RestTemplate.class );
    ReflectionTestUtils.setField( priceDAO, "restTemplate", restTemplate );
    ReflectionTestUtils.setField( priceDAO, "bitcoinPriceURL", "bitcoinPriceURL" );
    Mockito.when( restTemplate.getForObject( "bitcoinPriceURL", String.class ) )
      .thenThrow( new RestClientException( "Could not get response from external system" ) );
    final BitcoinPrice bitcoinPrice = priceDAO.getPrice();
    Assert.assertNull( bitcoinPrice );
    Mockito.verify( restTemplate, Mockito.only() ).getForObject( "bitcoinPriceURL", String.class );
  }

  @Test
  public void checkWhenExceptionFromObjectMapperCame() throws Exception
  {
    final InputStream inputStream = this.getClass().getResourceAsStream( "/bitcoinPriceResponse.json" );
    final String externalResponse = IOUtils.toString( inputStream, "UTF-8" );
    final PriceDAO priceDAO = new PriceDAOImpl();
    final RestTemplate restTemplate = Mockito.mock( RestTemplate.class );
    final ObjectMapper objectMapper = Mockito.mock( ObjectMapper.class );
    ReflectionTestUtils.setField( priceDAO, "restTemplate", restTemplate );
    ReflectionTestUtils.setField( priceDAO, "objectMapper", objectMapper );
    ReflectionTestUtils.setField( priceDAO, "bitcoinPriceURL", "bitcoinPriceURL" );
    Mockito.when( restTemplate.getForObject( "bitcoinPriceURL", String.class ) ).thenReturn( externalResponse );
    Mockito.when( objectMapper.readValue( externalResponse, BitcoinPrice.class) )
      .thenThrow( new JsonParseException( "unknown parse exception", null ) );
    final BitcoinPrice bitcoinPrice = priceDAO.getPrice();
    Assert.assertNull( bitcoinPrice );
    Mockito.verify( restTemplate, Mockito.only() ).getForObject( "bitcoinPriceURL", String.class );
  }
}
