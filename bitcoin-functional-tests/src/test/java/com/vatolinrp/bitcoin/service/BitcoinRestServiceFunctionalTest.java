package com.vatolinrp.bitcoin.service;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class BitcoinRestServiceFunctionalTest
{
  @Test
  public void testPing()
  {
    final HttpClient client = HttpClients.createDefault();
    final HttpGet request = new HttpGet( "http://localhost:8081/ping" );
    HttpResponse response = null;
    try {
      response = client.execute(request);
    } catch ( final IOException io ) {
      Assert.fail( "Exception took place when tried to execute get request for service status" );
    }
    Assert.assertNotNull( response );
    Assert.assertNotNull( response.getStatusLine() );
    Assert.assertEquals( response.getStatusLine().getStatusCode(), HttpStatus.SC_OK );
    String rawResponse = null;
    try {
      rawResponse = EntityUtils.toString( response.getEntity() );
    } catch ( final IOException io ) {
      Assert.fail( "Exception took place when tried to retrieve the response" );
    }
    Assert.assertNotNull( rawResponse );
    final JSONObject jsonObject = new JSONObject( rawResponse );
    final String statusValue = jsonObject.getString( "status" );
    Assert.assertNotNull( statusValue );
    Assert.assertEquals( statusValue, "Active" );
  }
}
