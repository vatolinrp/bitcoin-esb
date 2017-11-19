package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.model.BitcoinPriceValues;
import com.vatolinrp.bitcoin.model.Ping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration( locations = { "classpath:integr-testing.xml" } )
public class BitcoinRestServiceIntegrationTest extends AbstractTestNGSpringContextTests
{
  @Value("${rest.service.host}")
  private String restServiceHost;

  @Value("${rest.service.port}")
  private int restServicePort;

  final RestTemplate restTemplate = new RestTemplate();

  @Test
  public void checkBitcoinGet()
  {
    ResponseEntity<BitcoinPriceValues> response = null;
    try {
      final String url = "http://" + restServiceHost + ":" + restServicePort + "/bitcoin";
      response = restTemplate.getForEntity( url, BitcoinPriceValues.class );
    } catch ( final RestClientException io ) {
      Assert.fail( "Exception took place when tried to execute get request to service" );
    }
    Assert.assertNotNull( response );
    Assert.assertEquals( response.getStatusCode(), HttpStatus.OK );
    Assert.assertNotNull( response.getBody() );
    Assert.assertNotNull( response.getBody().getPrices() );
    Assert.assertEquals( response.getBody().getPrices().size(), 3 );
    Assert.assertNotNull( response.getBody().getPrices().get( "USD" ) );
    Assert.assertNotNull( response.getBody().getPrices().get( "CNY" ) );
    Assert.assertNotNull( response.getBody().getPrices().get( "EUR" ) );
  }

  @Test
  public void checkPing()
  {
    ResponseEntity<Ping> response = null;
    try {
      final String url = "http://" + restServiceHost + ":" + restServicePort + "/ping";
      response = restTemplate.getForEntity( url, Ping.class );
    } catch ( final RestClientException io ) {
      Assert.fail( "Exception took place when tried to execute get request to service" );
    }
    Assert.assertNotNull( response );
    Assert.assertEquals( response.getStatusCode(), HttpStatus.OK );
    Assert.assertNotNull( response.getBody() );
    Assert.assertNotNull( response.getBody().getStatus() );
    Assert.assertEquals( response.getBody().getStatus(), "Active" );
  }
}
