package com.bosha.dapp.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="星星之火图片")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksStarImg {
    @ApiModelProperty(value="")
    private Long id;

    @ApiModelProperty(value="")
    private Long sparksStarId;

    @ApiModelProperty(value="")
    private String url;
}