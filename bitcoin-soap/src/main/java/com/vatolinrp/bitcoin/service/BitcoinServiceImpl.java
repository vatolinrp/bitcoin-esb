package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.dao.PriceDAO;
import com.vatolinrp.bitcoin.generated.service.BitcoinPricesResponse;
import com.vatolinrp.bitcoin.generated.service.BitcoinServiceInterface;
import com.vatolinrp.bitcoin.model.blockchain.BitcoinPrice;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;

@WebService( targetNamespace = "http://bitcoin.vatolinrp.com/wsdl/BitcoinService",
             endpointInterface = "com.vatolinrp.bitcoin.generated.service.BitcoinServiceInterface",
             portName = "BitcoinServicePortHttp",
             serviceName = "BitcoinService")
@Component
public class BitcoinServiceImpl implements BitcoinServiceInterface
{
  @Resource
  private PriceDAO priceDAO;

  @Override
  public BitcoinPricesResponse getBitcoinPrices()
  {
    BitcoinPricesResponse bitcoinPricesResponse = new BitcoinPricesResponse();
    BitcoinPrice bitcoinPrice = priceDAO.getPrice();

    if( bitcoinPrice.getUsd() != null ) {
      bitcoinPricesResponse.setUsd( bitcoinPrice.getUsd().getLast() );
    }

    if( bitcoinPrice.getCny() != null ) {
      bitcoinPricesResponse.setCny( bitcoinPrice.getCny().getLast() );
    }

    if( bitcoinPrice.getEur() != null ) {
      bitcoinPricesResponse.setEur( bitcoinPrice.getEur().getLast() );
    }

    return bitcoinPricesResponse;
  }
}
