package com.bosha.common.api.dto;

import com.bosha.common.api.entity.PushMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveInvitePushDto
 * @Author liqingping
 * @Date 2020-03-25 10:33
 */
@Data
public class LiveInvitePushDto extends PushMessage {

    @ApiModelProperty("发起人昵称")
    private String nickName;
    @ApiModelProperty("发起人头像")
    private String headImg;
}
