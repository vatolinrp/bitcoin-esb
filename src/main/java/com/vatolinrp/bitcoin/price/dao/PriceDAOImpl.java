package com.vatolinrp.bitcoin.price.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vatolinrp.bitcoin.price.model.BitcoinPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class PriceDAOImpl implements PriceDAO
{
  private static final Logger logger = LoggerFactory.getLogger( PriceDAOImpl.class );

  @Value("${bitcoin.price.get.request.url}")
  private String bitcoinPriceURL;

  private RestTemplate restTemplate;

  private ObjectMapper objectMapper;

  public PriceDAOImpl()
  {
    restTemplate = new RestTemplate();
    objectMapper = new ObjectMapper();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BitcoinPrice getPrice()
  {
    return parseResponse( getRawResponseFromExternal() );
  }

  private BitcoinPrice parseResponse( String response )
  {
    if( response == null ) {
      logger.error( "Cannot parse response because it is null" );
      return null;
    }
    try {
      return objectMapper.readValue( response, BitcoinPrice.class );
    } catch ( Exception e) {
      logger.error( "An exception has occurred while working with response", e );
    }
    return null;
  }

  private String getRawResponseFromExternal()
  {
    try {
      return restTemplate.getForObject( bitcoinPriceURL, String.class );
    } catch ( Exception e ) {
      logger.error( "An exception has occurred while requesting prices", e );
    }
    return null;
  }
}
