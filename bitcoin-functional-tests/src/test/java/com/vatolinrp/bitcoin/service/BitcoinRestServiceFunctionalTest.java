package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.model.Ping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BitcoinRestServiceFunctionalTest
{
  @Test
  public void testPing()
  {
    final RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Ping> response = null;
    try {
      response = restTemplate.getForEntity( "http://localhost:8081/ping", Ping.class );
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
