package com.vatolinrp.bitcoin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( value="Ping response", description = "Service state description" )
public class Ping
{
  @ApiModelProperty( value="Service status", example="Active" )
  private String status;

  public String getStatus()
  {
    return status;
  }

  public void setStatus( final String status )
  {
    this.status = status;
  }
}
