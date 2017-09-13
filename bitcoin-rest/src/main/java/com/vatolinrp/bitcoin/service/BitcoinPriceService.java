package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.dao.PriceDAO;
import com.vatolinrp.bitcoin.model.BitcoinPriceValues;
import com.vatolinrp.bitcoin.model.blockchain.BitcoinPrice;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Produces( MediaType.APPLICATION_JSON )
@CrossOriginResourceSharing( allowAllOrigins = true )
@Path( "/" )
public class BitcoinPriceService
{
  @Resource
  private PriceDAO priceDAO;

  @GET
  @Path("/bitcoin")
  public BitcoinPriceValues getBitcoinPrices()
  {
    BitcoinPriceValues bitcoinPriceValues = new BitcoinPriceValues();
    BitcoinPrice bitcoinPrice = priceDAO.getPrice();

    if( bitcoinPrice.getUsd() != null ) {
      bitcoinPriceValues.setUsd( bitcoinPrice.getUsd().getLast() );
    }
    if( bitcoinPrice.getCny() != null ) {
      bitcoinPriceValues.setCny( bitcoinPrice.getCny().getLast() );
    }
    if( bitcoinPrice.getEur() != null ) {
      bitcoinPriceValues.setEur( bitcoinPrice.getEur().getLast() );
    }
    return bitcoinPriceValues;
  }
}
