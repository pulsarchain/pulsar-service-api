package com.bosha.common.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(value = "资源")
@Data
public class FileResource {
    @ApiModelProperty(value = "null")
    private Long id;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String name;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 路径
     */
    @ApiModelProperty(value = "路径")
    private String url;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    private Long size;

    /**
     * 高
     */
    @ApiModelProperty(value = "高")
    private Long height;

    /**
     * 宽
     */
    @ApiModelProperty(value = "宽")
    private Long width;

    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String mimeType;

    /**
     * 文件格式
     */
    @ApiModelProperty(value = "文件格式")
    private String format;

    /**
     * 1正常，0删除
     */
    @ApiModelProperty(value = "1正常，0删除")
    private Byte status;
}