package com.vatolinrp.bitcoin.price.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties( ignoreUnknown=true )
public class BitcoinPrice implements Serializable
{
  private final static long serialVersionUID = 3300771043239850211L;

  @JsonProperty("USD")
  private Currency usd;

  @JsonProperty("CNY")
  private Currency cny;

  @JsonProperty("EUR")
  private Currency eur;

  public Currency getUsd() {
    return usd;
  }

  public void setUsd(Currency usd) {
    this.usd = usd;
  }

  public Currency getCny() {
    return cny;
  }

  public void setCny(Currency cny) {
    this.cny = cny;
  }

  public Currency getEur() {
    return eur;
  }

  public void setEur(Currency eur) {
    this.eur = eur;
  }
}
