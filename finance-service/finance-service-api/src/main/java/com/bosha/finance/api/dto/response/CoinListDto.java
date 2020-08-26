package com.bosha.finance.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@ApiModel("后台币种列表")
public class CoinListDto implements Serializable {
    private Long id;

    /**
     * 中文名
     */
    @ApiModelProperty(value="中文名")
    private String cnName;

    /**
     * 币种
     */
    @ApiModelProperty(value="币种")
    private String symbolName;

    /**
     * 提现状态：0 关闭，1 开启
     */
    @ApiModelProperty(value="提现状态：0 关闭，1 开启")
    private Integer withdrawStatus;

    /**
     * 充值状态：0 关闭，1 开启
     */
    @ApiModelProperty(value="充值状态：0 关闭，1 开启")
    private Integer depositStatus;

    /**
     * 提现最大值
     */
    @ApiModelProperty(value="提现最大值")
    private BigDecimal withdrawMax;

    /**
     * 提现最小值
     */
    @ApiModelProperty(value="提现最小值")
    private BigDecimal withdrawMin;

    /**
     * 合约地址
     */
    @ApiModelProperty(value="合约地址")
    private String contractAddress;

    /**
     * logo地址
     */
    @ApiModelProperty(value="logo地址")
    private String logo;

    /**
     * 币种类型id，关联dict表的id
     */
    @ApiModelProperty(value="公链币id")
    private Long typeId;

    /**
     * 币种类型id，关联dict表的id
     */
    @ApiModelProperty(value="公链币")
    private String typeName;

    /**
     * 手续费
     */
    @ApiModelProperty(value="手续费")
    private BigDecimal fee;

    /**
     * 上下架状态：0 下架，1 上架
     */
    @ApiModelProperty(value="上下架状态：0 下架，1 上架")
    private Integer status;

    /**
     * 排序：数字越小越靠前
     */
    @ApiModelProperty(value="排序：数字越小越靠前")
    private Integer sort;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;
}
