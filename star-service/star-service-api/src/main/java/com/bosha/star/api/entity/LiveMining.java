package com.bosha.star.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import com.bosha.star.api.constants.StarServiceConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel(value = "直播挖矿")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveMining implements Serializable {

    public static final int STATUS_CONFIRMING = 0, STATUS_TO_BE_LIVE = 1, STATUS_LIVING = 2, STATUS_FINISHED = 3;

    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 主播地址
     */
    @ApiModelProperty(value = "主播地址")
    private String address;
    /**
     * 系统打币地址
     */
    @ApiModelProperty(value = "系统打币地址", required = true)
    @NotBlank(message = "系统打币地址不可为空")
    private String systemAddress;
    /**
     * 慈善地址
     */
    @ApiModelProperty(value = "慈善地址")
    private String charityAddress;
    /**
     * 合约地址
     */
    @ApiModelProperty(value = "合约地址")
    private String contractAddress;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题，最多20个字", required = true)
    @NotBlank(message = "标题不可为空")
    private String title;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介，最多200个字", required = true)
    @NotBlank(message = "简介不可为空")
    private String intro;

    /**
     * 封面图
     */
    @ApiModelProperty(value = "封面图", required = true)
    @NotBlank(message = "封面图不可为空")
    private String coverImg;

    /**
     * 直播开始时间
     */
    @ApiModelProperty(value = "直播开始时间，格式：yyyy-MM-dd HH:mm:00，\n时间范围必须是 30天以内，10分钟以后", required = true)
    @NotNull(message = "直播开始时间不可为空")
    @DateTimeFormat(pattern = StarServiceConstants.SECOND_FORMAT)
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Future(message = "开播时间必须大于当前时间")
    private Date liveStartTime;

    /**
     * 直播结束时间
     */
    @ApiModelProperty(value = "直播结束时间，格式：yyyy-MM-dd HH:mm:00", required = true)
    @NotNull(message = "直播结束时间不可为空")
    @DateTimeFormat(pattern = StarServiceConstants.SECOND_FORMAT)
    @JsonFormat(pattern = StarServiceConstants.SECOND_FORMAT, timezone = "GMT+8")
    @Future(message = "开播时间必须大于当前时间")
    private Date liveEndTime;

    /**
     * 实际开播时间
     */
    @ApiModelProperty(value = "实际开播时间", hidden = true)
    @JsonIgnore
    private Date actualLiveStartTime;

    /**
     * 投入燃料
     */
    @ApiModelProperty(value = "投入燃料，最小值为 3 PUL", required = true)
    @NotNull(message = "投入燃料不可为空")
    private BigDecimal amount;

    /**
     * 剩余燃料
     */
    @ApiModelProperty(value = "剩余燃料")
    private BigDecimal leftAmount;

    /**
     * 直播额外费用
     */
    @ApiModelProperty(value = "直播额外费用，直播的开始和结束的分钟差 乘以 每分钟的费用", required = true)
    @NotNull(message = "直播额外费用不可为空")
    private BigDecimal fee;

    /**
     * 状态：0 区块确认中，1 创建成功，等待开播，2 直播进行中，3 直播已结束
     */
    @ApiModelProperty(value = "状态：0 区块确认中，1 创建成功，等待开播，2 直播进行中，3 直播已结束")
    private Integer status;

    /**
     * 分享次数
     */
    @ApiModelProperty(value = "分享次数，最小值为1", required = true)
    @NotNull(message = "分享次数不可为空")
    @Min(value = 1, message = "分享次数最小值为1")
    private Integer shareNum;

    /**
     * 分享币数
     */
    @ApiModelProperty(value = "分享币数，单次最小值为 0.01，即 分享币除以分享次数必须大于等于 0.01", required = true)
    @NotNull(message = "分享币数不可为空")
    private BigDecimal sharePul;

    /**
     * 评论次数
     */
    @ApiModelProperty(value = "评论次数，最小值为1", required = true)
    @NotNull(message = "评论次数不可为空")
    @Min(value = 1, message = "评论次数最小值为1")
    private Integer commentNum;

    /**
     * 评论币数
     */
    @ApiModelProperty(value = "评论币数，单次最小值为 0.01，即 评论币数除以评论次数必须大于等于 0.01", required = true)
    @NotNull(message = "评论币数不可为空")
    private BigDecimal commentPul;

    /**
     * 观看奖励
     */
    @ApiModelProperty(value = "观看奖励，最小值为 0.01", required = true)
    @NotNull(message = "观看奖励不可为空")
    private BigDecimal view;

    /**
     * 信用分邀请最小值
     */
    @ApiModelProperty(value = "信用分邀请最小值", required = true)
    @NotNull(message = "信用分邀请最小值不可为空")
    @Min(value = 0, message = "最小为0")
    private Integer inviteStart;

    /**
     * 信用分邀请最大值
     */
    @ApiModelProperty(value = "信用分邀请最大值", required = true)
    @NotNull(message = "信用分邀请最大值不可为空")
    @Max(value = 100, message = "最大为100")
    private Integer inviteEnd;

    /**
     * 在线人数
     */
    @ApiModelProperty(value = "在线人数")
    private Integer onlineNum;

    /**
     * 挖矿人数
     */
    @ApiModelProperty(value = "挖矿人数")
    private Integer minerNum;

    /**
     * 打币hash
     */
    @ApiModelProperty(value = "打币hash", required = true)
    @NotBlank(message = "打币hash不可为空")
    private String hash;

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

    private static final long serialVersionUID = 1L;

}