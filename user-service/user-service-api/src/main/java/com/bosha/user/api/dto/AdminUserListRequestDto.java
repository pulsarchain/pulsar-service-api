package com.bosha.user.api.dto;

import java.io.Serializable;


import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AdminUserListRequestDto
 * @Author liqingping
 * @Date 2019-04-13 11:43
 */
@Data
@ApiModel
public class AdminUserListRequestDto extends Page implements Serializable {
    private static final long serialVersionUID = 5960347490431892410L;

    @ApiModelProperty("用户名")
    private String loginAccount;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("岗位id")
    private Integer positionId;

}
