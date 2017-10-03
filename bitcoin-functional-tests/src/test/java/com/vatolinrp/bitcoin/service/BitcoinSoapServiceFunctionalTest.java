package com.vatolinrp.bitcoin.service;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BitcoinSoapServiceFunctionalTest
{
  @Test
  public void testGetOfWsdl()
  {
    final HttpClient client = HttpClients.createDefault();
    final HttpGet request = new HttpGet( "http://0.0.0.0:8080/BitcoinService?wsdl" );
    HttpResponse response = null;
    try {
      response = client.execute( request );
    } catch ( final IOException io ) {
      Assert.fail( "Exception took place when tried to execute get request for service wsdl" );
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
    XPathFactory xpathFactory = XPathFactory.newInstance();
    XPath xpath = xpathFactory.newXPath();
    XPathExpression expr = null;
    try {
      expr = xpath.compile("/definitions/service/@name");
    } catch ( final XPathExpressionException e) {
      Assert.fail( "Exception took place when tried to compile xpath expression" );
    }
    String serviceName = null;
    try {
      serviceName = (String)expr.evaluate( loadXMLFromString( rawResponse ), XPathConstants.STRING );
    } catch ( final XPathExpressionException e) {
      Assert.fail();
    }
    Assert.assertNotNull( serviceName );
    Assert.assertEquals( serviceName, "BitcoinService" );
  }

  private Document loadXMLFromString( final String xml )
  {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try{
      DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse( new ByteArrayInputStream( xml.getBytes() ) );
    } catch ( final Exception e) {
      Assert.fail();
    }
    return null;
  }
}
