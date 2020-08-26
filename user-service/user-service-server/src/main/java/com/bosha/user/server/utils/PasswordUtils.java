package com.bosha.user.server.utils;


import com.bosha.commons.exception.BaseException;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: PasswordUtils
 * @Author liqingping
 * @Date 2019-04-12 21:22
 */

public class PasswordUtils {

    public static void check(String str) {
        char[] srChar = str.toCharArray();
        boolean lowerCase = true;
        boolean upperCase = true;
        boolean number = true;
        boolean special = false;
        for (char c : srChar) {
            if (c >= 'a' && c <= 'z') {
                lowerCase = false;
                break;
            }
        }
        for (char c : srChar) {
            if (c >= 'A' && c <= 'Z') {
                upperCase = false;
                break;
            }
        }
        for (char c : srChar) {
            if (c >= '0' && c <= '9') {
                number = false;
                break;
            }
        }
        if (lowerCase || upperCase || number) {
            throw new BaseException("密码必须为6-20位的[数字+大小写字母]三者的组合");
        }
        for (char c : srChar) {
            if (!(c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')) {
                special = true;
                break;
            }
        }
        if (special) {
            throw new BaseException("密码不支持特殊符号");
        }
    }

}
