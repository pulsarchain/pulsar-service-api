package com.bosha.user.api.dto;

import com.bosha.user.api.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: ManagerUserListDto
 * @Author liqingping
 * @Date 2020-02-09 17:40
 */
@Data
@ApiModel
public class ManagerUserListDto extends User {
    private static final long serialVersionUID = 3249597666363549750L;

    @ApiModelProperty("信用分")
    private Integer creditScore;
}
