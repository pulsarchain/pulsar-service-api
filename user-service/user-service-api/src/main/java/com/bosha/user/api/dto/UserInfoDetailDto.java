package com.bosha.user.api.dto;

import java.util.Date;


import com.bosha.user.api.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: UserInfoDetailDto
 * @Author liqingping
 * @Date 2020-02-09 17:54
 */
@Data
@ApiModel
public class UserInfoDetailDto extends User {
    private static final long serialVersionUID = -2139639822665863916L;

    @ApiModelProperty("信用分")
    private CreditScoreDto creditScore;
    @ApiModelProperty("认证信息")
    private AuthenticationInfoDto authenticationInfo ;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreditScoreDto {
        @ApiModelProperty("信用分")
        private Integer creditScore;
        @ApiModelProperty("评估时间")
        private Date updateTime;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthenticationInfoDto {
        @ApiModelProperty("状态：null 未认证，1 已提交资料，待转账，2 自我认证成功，3 辅助认证进行中，4 已完成认证")
        private Integer status;
        @ApiModelProperty("认证类型：null 未认证，3 个人，4 企业，5 政府")
        private Integer type;
    }
}
