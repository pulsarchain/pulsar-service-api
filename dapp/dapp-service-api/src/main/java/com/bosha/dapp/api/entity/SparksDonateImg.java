package com.bosha.dapp.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-bosha-dapp-api-entity-SparksDonateImg")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksDonateImg {
    @ApiModelProperty(value="")
    private Long id;

    @ApiModelProperty(value="")
    private Long donateId;

    @ApiModelProperty(value="")
    private String url;
}