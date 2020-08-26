package com.bosha.star.server.utils;

import com.tencentyun.TLSSigAPIv2;

public class GenerateUserSig {

    public static String generate(Long sdkAppid, String key, String address, long expireTime) {
        TLSSigAPIv2 api = new TLSSigAPIv2(sdkAppid, key);
        return api.genSig(address, expireTime);
    }

}