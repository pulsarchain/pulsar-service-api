package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.Date;

@Data
@ApiModel
@ToString
public class UserTextFollowDto {
    private String followUserAddress;
    private String headImg;
    private String nickName;
    private Long textId;
    private String title;
    private Date pushTime;
    private Long pushTimeLong;

    public Long getPushTimeLong() {
        if (!StringUtils.isEmpty(pushTime)) {
            pushTimeLong = pushTime.getTime();
        }
        return pushTimeLong;
    }

    public void setPushTimeLong(Long pushTimeLong) {
        this.pushTimeLong = pushTimeLong;
    }
}
