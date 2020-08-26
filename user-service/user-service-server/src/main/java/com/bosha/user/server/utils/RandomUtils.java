package com.bosha.user.server.utils;

import java.math.BigDecimal;

public class RandomUtils {
    private static final int SCALE = 2;

    public static BigDecimal getRandomNumber(BigDecimal remainingNumber, Integer remainingPeople) {
        BigDecimal bigDecimalPeople = new BigDecimal(remainingPeople);
        BigDecimal base = new BigDecimal(SCALE);
        double max = remainingNumber.divide(bigDecimalPeople, SCALE, BigDecimal.ROUND_HALF_UP).multiply(base).doubleValue();
        double min = 0.01D;
        double A = max - min;
        double B = (Math.random() * A) + min;
        BigDecimal decimal = new BigDecimal(B).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
        return decimal;
    }
}
