package com.vatolinrp.bitcoin.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

@ContextConfiguration( locations = { "classpath:funct-testing.xml" } )
public class BitcoinRestServiceFunctionalTest extends AbstractTestNGSpringContextTests
{
  @Value("${wiremock.host}")
  private String wiremockHost;

  @Value("${wiremock.port}")
  private int wiremockPort;

  @Value("${rest.service.port}")
  private int restServicePort;

  @Value("${rest.service.host}")
  private String restServiceHost;

  private WireMockServer wireMockServer;

  private static final RestTemplate restTemplate = new RestTemplate();

  private static String pingUrl;
  private static String getUrl;

  private BlockChainStubs blockChainStubs;

  @BeforeClass
  private void setup() throws IOException
  {
    wireMockServer = new WireMockServer( wiremockPort );
    wireMockServer.start();
    WireMock.configureFor( wiremockHost, wiremockPort );
    blockChainStubs = new BlockChainStubs();
    pingUrl = "http://" + restServiceHost + ":" + restServicePort + "/ping";
    getUrl = "http://" + restServiceHost + ":" + restServicePort + "/bitcoin";
  }

  @AfterMethod
  private void reset()
  {
    WireMock.reset();
  }

  @AfterClass
  private void shutdown()
  {
    wireMockServer.stop();
  }

  private void checkRestService()
  {
    ResponseEntity<Ping> response = null;
    try {
      response = restTemplate.getForEntity( pingUrl, Ping.class );
    } catch ( final RestClientException io ) {
      Assert.fail();
    }
    if( response != null && response.getBody() != null ) {
      Assert.assertTrue( "Active".equals( response.getBody().getStatus() ) );
    }
  }

  @Test
  public void checkSuccessfulGet()
  {
    ResponseEntity<BitcoinPriceValues> response = null;
    blockChainStubs.stubExternalForSuccessResponse();
    checkRestService();
    try {
      response = restTemplate.getForEntity( getUrl, BitcoinPriceValues.class );
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
  public void checkGetWhenServerErrorReturnedOnExternalServiceCall()
  {
    ResponseEntity<BitcoinPriceValues> response = null;
    blockChainStubs.stubExternalForServerError();
    checkRestService();
    try {
      response = restTemplate.getForEntity( getUrl, BitcoinPriceValues.class );
    } catch ( final RestClientException io ) {
      Assert.fail( "Exception took place when tried to execute get request to service" );
    }
    Assert.assertNotNull( response );
    Assert.assertEquals( response.getStatusCode(), HttpStatus.OK );
    Assert.assertNotNull( response.getBody() );
    Assert.assertNotNull( response.getBody().getPrices() );
    Assert.assertTrue( response.getBody().getPrices().isEmpty() );
  }

  @Test
  public void checkGetWhenClientErrorReturnedOnExternalServiceCall()
  {
    ResponseEntity<BitcoinPriceValues> response = null;
    blockChainStubs.stubExternalForClientError();
    checkRestService();
    try {
      response = restTemplate.getForEntity( getUrl, BitcoinPriceValues.class );
    } catch ( final RestClientException io ) {
      Assert.fail( "Exception took place when tried to execute get request to service" );
    }
    Assert.assertNotNull( response );
    Assert.assertEquals( response.getStatusCode(), HttpStatus.OK );
    Assert.assertNotNull( response.getBody() );
    Assert.assertNotNull( response.getBody().getPrices() );
    Assert.assertTrue( response.getBody().getPrices().isEmpty() );
  }
}
