package com.bosha.dapp.api.entity;

import java.math.BigDecimal;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="邀请见证")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksWitness {
    @ApiModelProperty(value="")
    private Long id;

    /**
    * 用户地址
    */
    @ApiModelProperty(value="用户地址",required = true)
    private String address;

    /**
    * 见证地址
    */
    @ApiModelProperty(value="见证地址",required = true)
    private String witnessAddress;

    /**
    * 类型：1 点亮，2 擦亮，3 造星，4 机构，5 基金
    */
    @ApiModelProperty(value="类型：1 点亮，2 擦亮，3 造星，4 机构，5 基金",required = true)
    private Integer type;

    /**
    * 关联的id
    */
    @ApiModelProperty(value="关联的id")
    private Long releatedId;

    /**
     * 金额
     */
    @ApiModelProperty(value="金额")
    private BigDecimal amount;

    @ApiModelProperty(value="hash",required = true)
    private String hash;

    @ApiModelProperty(value="额外数据：json字符串")
    private String extra;

    @ApiModelProperty(value="")
    private Date createTime;

    @ApiModelProperty(value="")
    private Date updateTime;

}