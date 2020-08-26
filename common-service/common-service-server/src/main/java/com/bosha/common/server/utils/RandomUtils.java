package com.bosha.common.server.utils;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;


/**
 * User: rizenguo Date: 2014/10/29 Time: 14:18
 *
 * @author liuwenzhong
 */
public class RandomUtils {

    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomIntByLength(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static BigDecimal getRandom(){
        double min = 0.001;//最小值
        double max = 10;//最大值
        int scl =  3;//小数最大位数
        int pow = (int) Math.pow(10, scl);//指定小数位
        double one = Math.floor((Math.random() * (max - min) + min) * pow) / pow;
        return new BigDecimal(one).setScale(3,BigDecimal.ROUND_HALF_UP);
    }

    public static void main(String[] args) {
        System.out.println(getRandom());
    }

    public static String UUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}
