package com.vatolinrp.bitcoin.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
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

public class BitcoinSoapServiceFunctionalTest
{
  @Test
  public void testGetOfWsdl()
  {
    final RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = null;
    try {
      response = restTemplate.getForEntity( "http://0.0.0.0:8080/BitcoinService?wsdl", String.class );
    } catch ( final RestClientException io ) {
      Assert.fail( "Exception took place when tried to execute get request for service wsdl" );
    }
    Assert.assertNotNull( response );
    Assert.assertEquals( response.getStatusCode(), HttpStatus.OK );
    Assert.assertNotNull( response.getBody() );

    final String rawResponse = response.getBody();
    final XPathFactory xpathFactory = XPathFactory.newInstance();
    final XPath xpath = xpathFactory.newXPath();
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
      Assert.fail( "Exception took place when tried to evaluate xpath expression" );
    }
    Assert.assertNotNull( serviceName );
    Assert.assertEquals( serviceName, "BitcoinService" );
  }

  private Document loadXMLFromString( final String xml )
  {
    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try{
      final DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse( new ByteArrayInputStream( xml.getBytes() ) );
    } catch ( final Exception e) {
      Assert.fail( "Exception occurred when parsing was taken place" );
    }
    return null;
  }
}
