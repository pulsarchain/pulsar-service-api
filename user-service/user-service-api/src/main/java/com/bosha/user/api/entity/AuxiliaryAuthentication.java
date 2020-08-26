package com.bosha.user.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="辅助认证")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuxiliaryAuthentication implements Serializable {
    @ApiModelProperty(value="主键id")
    private Long id;

    /**
    * 关联的认证表id
    */
    @ApiModelProperty(value="关联的认证表id")
    private Long auId;

    /**
     * 用户地址
     */
    @ApiModelProperty(value="用户地址")
    private String address;

    /**
    * 辅助认证地址
    */
    @ApiModelProperty(value="辅助认证地址")
    private String auxiliaryAddress;

    /**
    * 金额
    */
    @ApiModelProperty(value="金额")
    private BigDecimal money;

    /**
     * 交易hash
     */
    @ApiModelProperty(value="交易hash")
    private String hash;
    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 到账时间
    */
    @ApiModelProperty(value="到账时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}