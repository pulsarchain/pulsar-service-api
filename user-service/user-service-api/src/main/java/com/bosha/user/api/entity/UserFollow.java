package com.bosha.user.api.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFollow {
    /**
    * id
    */
    private Long id;

    /**
    * 用户Id
    */
    private String userAddress;

    /**
    * 关注用户Id
    */
    private String followUserAddress;

    /**
    * 昵称备注
    */
    private String remark;

    /**
    * 是否关注1，关注 ，0未关注
    */
    private Integer follow;

    /**
    * 是否好友1.好友，0非好友
    */
    private Integer friend;

    /**
    * 关注时间
    */
    private Date createTime;
}