package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.dao.PriceDAO;
import com.vatolinrp.bitcoin.model.BitcoinPriceValues;
import com.vatolinrp.bitcoin.model.blockchain.BitcoinPrice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api( value = "/", description = "Api for getting bitcoin current price" )
public class BitcoinPriceService
{
  @Resource
  private PriceDAO priceDAO;

  @GET
  @Path("/bitcoin")
  @ApiOperation( value = "Get bitcoin price in different currencies", response = BitcoinPriceValues.class )
  @ApiResponses( {
    @ApiResponse( code = 200, message = "Operation finished successfully", response = BitcoinPriceValues.class ),
    @ApiResponse( code = 500, message = "Internal server error" )
  } )
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
