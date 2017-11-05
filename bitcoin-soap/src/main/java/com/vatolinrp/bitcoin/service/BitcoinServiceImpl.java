package com.vatolinrp.bitcoin.service;

import com.vatolinrp.bitcoin.dao.PriceDAO;
import com.vatolinrp.bitcoin.generated.service.BitcoinPricesResponse;
import com.vatolinrp.bitcoin.generated.service.BitcoinServiceInterface;
import com.vatolinrp.bitcoin.generated.service.PriceResult;
import com.vatolinrp.bitcoin.model.CurrencyCodeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.Map;

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
    final BitcoinPricesResponse bitcoinPricesResponse = new BitcoinPricesResponse();
    final Map<CurrencyCodeEnum, Double> bitcoinPriceMap = priceDAO.getPrice();
    bitcoinPriceMap.entrySet().forEach( entry -> {
      final PriceResult priceResult = new PriceResult();
      priceResult.setCurrencyCode( entry.getKey().name() );
      priceResult.setValue( entry.getValue() );
      bitcoinPricesResponse.getPriceResult().add( priceResult );
    });
    return bitcoinPricesResponse;
  }
}
