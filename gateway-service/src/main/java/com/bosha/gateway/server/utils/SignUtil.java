package com.bosha.gateway.server.utils;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 签名校验工具类
 *
 * @author xiaodong
 */
public class SignUtil {
    /**
     * 校验签名
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return
     */
    public static boolean check(String key, String signature, String timestamp, String nonce) {
        if (signature == null || timestamp == null || nonce == null) {
            return false;
        }
        //对token,timestamp nonce 按字典排序
        String[] paramArr = new String[]{key, timestamp, nonce};
        Arrays.sort(paramArr);

        //将排序后的结果拼接成一个字符串
        String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
        String ciphertext = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            //对拼接后的字符串进行sha1加密
            byte[] digest = md.digest(content.getBytes());
            ciphertext = byteToStr(digest);
        } catch (Exception e) {
            return false;
        }
        //将sha1加密后的字符串与signature进行对比
        return ciphertext.equals(signature.toUpperCase());
    }

    /**
     * 生成签名 android使用
     *
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return
     */
    public static String getSignature(String key, String timestamp, String nonce) {
        //对token,timestamp nonce 按字典排序
        String[] paramArr = new String[]{key, timestamp, nonce};
        Arrays.sort(paramArr);

        //将排序后的结果拼接成一个字符串
        String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
        String ciphertext = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            //对拼接后的字符串进行sha1加密 update// 使用指定的字节数组对摘要进行最后更新
            byte[] digest = md.digest(content.getBytes());//完成摘要计算
            ciphertext = byteToStr(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将sha1加密后的字符串与signature进行对比
        return ciphertext;
    }


    /**
     * 将字节数组转换成十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        StringBuilder strDigest = new StringBuilder();
        for (byte b : byteArray) {
            strDigest.append(byteToHexStr(b));
        }
        return strDigest.toString();
    }

    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        return new String(tempArr);
    }

}