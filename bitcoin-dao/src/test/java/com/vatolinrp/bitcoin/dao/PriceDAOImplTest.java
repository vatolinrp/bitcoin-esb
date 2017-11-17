package com.vatolinrp.bitcoin.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatolinrp.bitcoin.model.CurrencyCodeEnum;
import org.apache.commons.io.IOUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PriceDAOImplTest
{
  private static final String TEST_URL = "bitcoinPriceURL";

  @InjectMocks
  private PriceDAOImpl priceDAO;

  @Mock
  private RestTemplate restTemplate;

  @BeforeClass
  private void setup()
  {
    MockitoAnnotations.initMocks( this );
    ReflectionTestUtils.setField( priceDAO, "bitcoinPriceURL", TEST_URL );
  }

  @AfterMethod
  private void reset()
  {
    Mockito.reset( restTemplate );
  }

  @Test
  public void getPricesSuccessfulFlow() throws Exception
  {
    final InputStream inputStream = this.getClass().getResourceAsStream( "/bitcoinPriceResponse.json" );
    final String externalResponse = IOUtils.toString( inputStream, "UTF-8" );
    final Map expectedMap = new ObjectMapper().readValue( externalResponse, Map.class );
    Mockito.when( restTemplate.getForObject( TEST_URL, Map.class ) ).thenReturn( expectedMap );
    final Map<CurrencyCodeEnum, Double> bitcoinPriceMap = priceDAO.getPrice();
    Assert.assertNotNull( bitcoinPriceMap );
    Assert.assertEquals( bitcoinPriceMap.size(), 3 );
    Assert.assertTrue( bitcoinPriceMap.containsKey( CurrencyCodeEnum.USD ) );
    Assert.assertTrue( bitcoinPriceMap.containsKey( CurrencyCodeEnum.CNY ) );
    Assert.assertTrue( bitcoinPriceMap.containsKey( CurrencyCodeEnum.EUR ) );
    Assert.assertEquals( bitcoinPriceMap.get( CurrencyCodeEnum.USD ), 2778.93 );
    Assert.assertEquals( bitcoinPriceMap.get( CurrencyCodeEnum.EUR ), 2340.75 );
    Assert.assertEquals( bitcoinPriceMap.get( CurrencyCodeEnum.CNY ), 18641.62 );
    Mockito.verify( restTemplate, Mockito.only() ).getForObject( TEST_URL, Map.class );
  }

  @Test
  public void checkWhenExceptionFromRestTemplateCame() throws Exception
  {
    Mockito.when( restTemplate.getForObject( TEST_URL, Map.class ) )
      .thenThrow( new RestClientException( "Could not get response from external system" ) );
    final Map<CurrencyCodeEnum, Double> bitcoinPriceMap = priceDAO.getPrice();
    Assert.assertNotNull( bitcoinPriceMap );
    Assert.assertTrue( bitcoinPriceMap.isEmpty() );
    Mockito.verify( restTemplate, Mockito.only() ).getForObject( TEST_URL, Map.class );
  }

  @Test
  public void checkWhenNullRestTemplateCame() throws Exception
  {
    Mockito.when( restTemplate.getForObject( TEST_URL, Map.class ) ).thenReturn( null );
    final Map<CurrencyCodeEnum, Double> bitcoinPriceMap = priceDAO.getPrice();
    Assert.assertNotNull( bitcoinPriceMap );
    Assert.assertTrue( bitcoinPriceMap.isEmpty() );
    Mockito.verify( restTemplate, Mockito.only() ).getForObject( TEST_URL, Map.class );
  }

  @Test
  public void checkWhenClassCastExceptionOccurred() throws Exception
  {
    final Map<String, String> notExpectedMap = new HashMap<>();
    notExpectedMap.put( CurrencyCodeEnum.USD.name(), "testValue" );
    Mockito.when( restTemplate.getForObject( TEST_URL, Map.class ) ).thenReturn( notExpectedMap );
    final Map<CurrencyCodeEnum, Double> bitcoinPriceMap = priceDAO.getPrice();
    Assert.assertNotNull( bitcoinPriceMap );
    Assert.assertTrue( bitcoinPriceMap.isEmpty() );
    Mockito.verify( restTemplate, Mockito.only() ).getForObject( TEST_URL, Map.class );
  }
}
