package com.bosha.dapp.api.entity;

import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="dapp收藏")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DappFavorite {
    @ApiModelProperty(value="")
    private String address;

    @ApiModelProperty(value="")
    private Long dappId;

    @ApiModelProperty(value="")
    private Date createTime;
}