package com.work.borrow.util;

import org.springframework.util.DigestUtils;

import java.util.Random;

/**
 * 字符操作工具类
 */
public class CharUtils {
    /**
     * 字符集字典
     */
    private static final String CHARA_CHARAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /**
     * 数字字典
     */
    private static final String NUM_CHARAS = "0123456789";


    /**
     * 生成数字随机码，可以为0开头
     * @param length 随机码长度
     * @return 对应随机码长度的随机码
     */
    public static String randomNumCode(int length) {
        return randomCode(length,NUM_CHARAS);
    }

    /**
     * 生成随机种子为A-Z、a-z、0-9的随机码
     * @param length 随机码长度
     * @return 对应随机码长度的随机码
     */
    public static String randomCharCode(int length) {
        return randomCode(length,CHARA_CHARAS+NUM_CHARAS);
    }

    /**
     * 字符MD5加密
     * @param code
     * @return
     */
    public static String charMD5Hex(String code) {
        return  DigestUtils.md5DigestAsHex(code.getBytes());
    }
    /**
     * 生成随机码
     * @param length 随机码长度
     * @param charas 随机码种子
     * @return 根据随机码种子生成的对应随机码长度的随机码
     */
    private static String randomCode(int length,String charas) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<length; i++) {
            char nextChar = charas.charAt(random.nextInt(charas.length()));
            stringBuilder.append(nextChar);
        }
        return stringBuilder.toString();
    }


}
