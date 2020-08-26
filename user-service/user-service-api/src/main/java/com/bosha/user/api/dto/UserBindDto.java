package com.bosha.user.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: UserBindDto
 * @Author liqingping
 * @Date 2019-12-16 14:25
 */
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class UserBindDto   implements Serializable {
    private static final long serialVersionUID = -6326228889286842556L;

    @ApiModelProperty(value = "地址", required = true)
    @NotBlank(message = "地址不可为空")
    @Length(min = 30, max = 50, message = "incorrect address")
    private String address;
}
