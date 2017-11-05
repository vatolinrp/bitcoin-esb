package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.model.BitcoinPriceValues;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BitcoinRestServiceIntegrationTest
{
  @Test
  public void checkBitcoinGet()
  {
    final RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<BitcoinPriceValues> response = null;
    try {
      response = restTemplate.getForEntity( "http://localhost:8081/bitcoin", BitcoinPriceValues.class );
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
}
