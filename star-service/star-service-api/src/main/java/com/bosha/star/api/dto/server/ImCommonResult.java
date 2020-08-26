package com.bosha.star.api.dto.server;

import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: ImCommonResult
 * @Author liqingping
 * @Date 2020-04-02 15:46
 */
@Data
public class ImCommonResult {

    private String ActionStatus;

    private String ErrorInfo;

    private Integer ErrorCode;

    public boolean isSuccess() {
        return "ok".equalsIgnoreCase(this.ActionStatus) && this.ErrorCode == 0;
    }
}
