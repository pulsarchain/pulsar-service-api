package com.bosha.user.api.dto;

import java.io.Serializable;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: UserManagerDetailDto
 * @Author liqingping
 * @Date 2019-12-16 16:02
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserManagerDetailDto implements Serializable {
    private static final long serialVersionUID = -3947061168508986462L;

    private UserBasicDto basic;
}
