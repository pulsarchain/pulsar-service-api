package com.bosha.user.server.utils;

import com.alibaba.fastjson.JSON;
import com.bosha.commons.utils.HttpUtils;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: IPUtil
 * @Author liqingping
 * @Date 2020-01-08 17:54
 */

public class IPUtil {

    private static final String URL = "http://ip-api.com/json/%s?lang=zh-CN";

    public static IPAddress address(String ip) {
        try {
            String body = HttpUtils.get(String.format(URL, ip));
            return JSON.parseObject(body, IPAddress.class);
        } catch (Exception e) {
            return new IPAddress();
        }
    }
}

@Data
class IPAddress {
    private String country;
    private String regionName;
    private String city;
    private String query;

    @Override
    public String toString() {
        return "[country=" + this.getCountry() + ", regionName=" + this.getRegionName() + ", city=" + this.getCity() + ", ip=" + this.getQuery() + "]";
    }

}
