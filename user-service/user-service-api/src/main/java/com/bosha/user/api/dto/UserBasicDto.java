package com.bosha.user.api.dto;

import com.bosha.user.api.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: UserBasicDto
 * @Author liqingping
 * @Date 2020-01-02 13:21
 */
@Data
@ApiModel
public class UserBasicDto extends User {

    private Integer  recommendUser;

    @ApiModelProperty("信用分")
    private Integer creditScore;
}
