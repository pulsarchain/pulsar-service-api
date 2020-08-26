package com.bosha.star.api.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveCallbackErrorMsgEnum
 * @Author liqingping
 * @Date 2020-03-27 16:34
 */

public class LiveCallbackErrorMsgEnum {

    public static final Map<Integer, String> MSG = new HashMap<>();

    static {
        MSG.put(1, "主播端主动断流");
        MSG.put(2, "主播端主动断流");
        MSG.put(3, "主播端主动断开 TCP 连接");
        MSG.put(4, "主播端 TCP 连接异常");
        MSG.put(7, "收到流数据异常");
    }
}
