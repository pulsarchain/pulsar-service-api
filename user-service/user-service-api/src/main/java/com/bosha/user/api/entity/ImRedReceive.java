package com.bosha.user.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com-bosha-user-api-entity-ImRedReceive")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImRedReceive {
    @ApiModelProperty(value="null")
    private Long id;

    /**
    * 转账hash
    */
    @ApiModelProperty(value="转账hash")
    private String hash;

    /**
    * 领取人
    */
    @ApiModelProperty(value="领取人")
    private String address;

    /**
    * 关联红包
    */
    @ApiModelProperty(value="关联红包")
    private Long imRedId;

    /**
    * 领取币数
    */
    @ApiModelProperty(value="领取币数")
    private BigDecimal money;

    /**
    * 1.已领取，2，未领取
    */
    @ApiModelProperty(value="1.已领取，2，未领取")
    private Integer status;

    /**
    * 领取时间
    */
    @ApiModelProperty(value="领取时间")
    private Date createTime;
}