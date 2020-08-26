package com.bosha.finance.server.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by shining on 2018/6/29.
 */
public class Arith {
    private static final int SCALE = 2;

    /**
     * 提供精确加法计算的add方法
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确减法运算的sub方法
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确乘法运算的mul方法
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param scale 精确范围
     * @param v1    被加数
     * @param v2    加数
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static BigDecimal div(Double v1, Double v2, int scale)  {
        //如果精确范围小于0，抛出异常信息
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static BigDecimal div(Double v1, Double v2) throws IllegalAccessException {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2, SCALE, BigDecimal.ROUND_HALF_UP);
    }
}
