package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.dao.PriceDAO;
import com.vatolinrp.bitcoin.model.BitcoinPriceValues;
import com.vatolinrp.bitcoin.model.CurrencyCodeEnum;
import com.vatolinrp.bitcoin.model.Ping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Service
@Produces( MediaType.APPLICATION_JSON )
@Path( "/" )
@Api( value = "/" )
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
    final BitcoinPriceValues bitcoinPriceValues = new BitcoinPriceValues();
    final Map<CurrencyCodeEnum, Double> bitcoinPriceMap = priceDAO.getPrice();
    bitcoinPriceMap.entrySet()
      .forEach( entry -> bitcoinPriceValues.getPrices().put( entry.getKey().name(), entry.getValue() ) );
    return bitcoinPriceValues;
  }

  @GET
  @Path("/ping")
  @ApiOperation( value = "Check system's health", response = Ping.class )
  @ApiResponses( {
    @ApiResponse( code = 200, message = "Operation finished successfully", response = Ping.class ),
    @ApiResponse( code = 500, message = "Internal server error" )
  } )
  public Ping ping()
  {
    final Ping ping = new Ping();
    ping.setStatus( "Active" );
    return ping;
  }
}
