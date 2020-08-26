package com.bosha.star.server.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: GenerateLiveStreamUrl
 * @Author liqingping
 * @Date 2020-03-28 10:27
 */
@Slf4j
public class GenerateLiveStreamUrl {

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /*
     * KEY+ streamName + txTime
     */
    public static String sign(String key, String streamName, long txTime) {
        String input = key + streamName + Long.toHexString(txTime).toUpperCase();
        String txSecret = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            txSecret = byteArrayToHexString(messageDigest.digest(input.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return txSecret == null ? "" : "txSecret=" + txSecret + "&txTime=" + Long.toHexString(txTime).toUpperCase();
    }

    private static String byteArrayToHexString(byte[] data) {
        char[] out = new char[data.length << 1];
        for (int i = 0, j = 0; i < data.length; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
    }
}
