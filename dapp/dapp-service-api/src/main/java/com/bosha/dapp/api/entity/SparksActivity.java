package com.bosha.dapp.api.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel(value = "活动（走进星星）")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksActivity {
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 机构id
     */
    @ApiModelProperty(value = "机构id")
    private Long orgId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @JsonIgnore
    private Integer status;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不可为空")
    private String name;

    /**
     * 参与人数
     */
    @ApiModelProperty(value = "参与人数", required = true)
    @NotNull(message = "参与人数不可为空")
    private Integer num;

    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式", required = true)
    @NotBlank(message = "联系方式不可为空")
    private String contact;

    /**
     * 交通工具
     */
    @ApiModelProperty(value = "交通工具", required = true)
    @NotBlank(message = "交通工具不可为空")
    private String vehicle;

    /**
     * 出发时间
     */
    @ApiModelProperty(value = "出发时间，格式：yyyy-MM-dd HH:mm:ss", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "出发时间不可为空")
    private Date time;

    /**
     * 出发地点
     */
    @ApiModelProperty(value = "出发地点", required = true)
    @NotBlank(message = "出发地点不可为空")
    private String location;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String latitude;

    /**
     * 活动类型
     */
    @ApiModelProperty(value = "活动类型", required = true)
    @NotBlank(message = "活动类型不可为空")
    private String type;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", required = true)
    @NotBlank(message = "描述不可为空")
    private String desc;

    /**
     * 信用分推送最小值
     */
    @ApiModelProperty(value = "信用分推送最小值")
    private Integer creditScoreMin;

    /**
     * 信用分推送最大值
     */
    @ApiModelProperty(value = "信用分推送最大值")
    private Integer creditScoreMax;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 地区id
     */
    @ApiModelProperty(value = "地区id")
    private Integer areaId;

    /**
     * hash
     */
    @ApiModelProperty(value="hash")
    private String hash;

    @ApiModelProperty(value = "图片列表", required = true)
    @NotEmpty(message = "图片列表不可为空")
    private List<String> imgs;
}