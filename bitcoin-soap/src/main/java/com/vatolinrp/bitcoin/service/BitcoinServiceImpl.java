package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.dao.PriceDAO;
import com.vatolinrp.bitcoin.generated.service.BitcoinPricesResponse;
import com.vatolinrp.bitcoin.generated.service.BitcoinServiceInterface;
import com.vatolinrp.bitcoin.generated.service.Currency;
import com.vatolinrp.bitcoin.model.BitcoinPrice;
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

    Currency currency = new Currency();
    if( bitcoinPrice.getUsd() != null ) {
      currency.setLast( bitcoinPrice.getUsd().getLast() );
      bitcoinPricesResponse.setUsd( currency );
    }

    currency = new Currency();
    if( bitcoinPrice.getCny() != null ) {
      currency.setLast( bitcoinPrice.getCny().getLast() );
      bitcoinPricesResponse.setCny( currency );
    }

    currency = new Currency();
    if( bitcoinPrice.getEur() != null ) {
      currency.setLast( bitcoinPrice.getEur().getLast() );
      bitcoinPricesResponse.setEur( currency );
    }

    return bitcoinPricesResponse;
  }
}
