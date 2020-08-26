package com.bosha.star.api.dto.server;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: ImGroupInfoResult
 * @Author liqingping
 * @Date 2020-04-02 16:46
 */
@Data
public class ImGroupInfoResult extends ImCommonResult {

    private Integer MemberNum = 0;

    private List<ImGroupMember> MemberList = new ArrayList<>();
}
