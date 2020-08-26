package com.bosha.user.api.dto;

import java.io.Serializable;


import com.bosha.user.api.entity.AdminUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AdminUserListDto
 * @Author liqingping
 * @Date 2019-04-13 11:40
 */
@Data
@ApiModel
public class AdminUserListDto extends AdminUser implements Serializable {
    private static final long serialVersionUID = 8971826235500334435L;

    @ApiModelProperty("岗位名")
    private String positionName;

}
