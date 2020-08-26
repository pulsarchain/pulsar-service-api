package com.bosha.dapp.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-bosha-dapp-api-entity-SparksOrgImg")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksOrgImg {
    @ApiModelProperty(value="")
    private Long orgId;

    @ApiModelProperty(value="")
    private String url;
}