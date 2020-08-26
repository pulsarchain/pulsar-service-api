package com.bosha.user.api.dto;

import java.io.Serializable;


import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: UserManagerListDto
 * @Author liqingping
 * @Date 2019-12-16 15:58
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserManagerListDto extends Page implements Serializable {
    private static final long serialVersionUID = 7039762125280274720L;

    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("用户地址")
    private String address;
    @ApiModelProperty("来源：1 安卓，2 iOS，3 web")
    private Integer source;
    @ApiModelProperty("创建时间开始，格式：yyyy-MM-dd HH:mm:ss")
    private String createTimeStart;
    @ApiModelProperty("创建时间结束，格式：yyyy-MM-dd HH:mm:ss")
    private String createTimeEnd;
}
