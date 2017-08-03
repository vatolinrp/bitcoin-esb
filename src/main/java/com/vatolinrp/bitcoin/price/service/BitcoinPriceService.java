package com.vatolinrp.bitcoin.price.service;

import com.vatolinrp.bitcoin.price.dao.PriceDAO;
import com.vatolinrp.bitcoin.price.model.BitcoinPrice;
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
  public BitcoinPrice getBitcoinPrices()
  {
    return priceDAO.getPrice();
  }
}
