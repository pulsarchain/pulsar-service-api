package com.bosha.dapp.api.entity;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-bosha-dapp-api-entity-DappReportVote")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DappReportVote {
    @ApiModelProperty(value = "", hidden = true)
    @JsonIgnore
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址", hidden = true)
    @JsonIgnore
    private String address;

    /**
     * 举报id
     */
    @ApiModelProperty(value = "举报id")
    @NotNull(message = "举报的id不可为空")
    private Long dappReportId;

    /**
     * 举报类型：0 假，1 真
     */
    @ApiModelProperty(value = "举报类型：0 假，1 真")
    @NotNull(message = "真假类型不可为空")
    @Min(value = 0, message = "最小为0")
    @Max(value = 1, message = "最大为1")
    private Integer type;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonIgnore
    private Date createTime;
}