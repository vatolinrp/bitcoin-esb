package com.vatolinrp.bitcoin.model.blockchain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Currency implements Serializable
{
  private static final long serialVersionUID = 1473857389102406100L;

  @JsonProperty("last")
  private Double last;

  public Double getLast()
  {
    return last;
  }

  public void setLast( final Double last )
  {
    this.last = last;
  }
}