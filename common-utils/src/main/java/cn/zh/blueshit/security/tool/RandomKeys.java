package cn.zh.blueshit.security.tool;

import org.apache.commons.lang.RandomStringUtils;

import java.util.Random;

/**
 * Created by zhaoheng on 2016/6/29.
 */
public class RandomKeys {

    private static final String[] keyByte = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};
    private static final int keyCount = 32;

    public RandomKeys() {
    }

    public static String initKey() {
        StringBuffer sbBuffer = new StringBuffer();

        for(int i = 0; i < keyCount; ++i) {
            Math.random();
            sbBuffer.append(keyByte[(new Random()).nextInt(keyByte.length)]);
        }

        System.out.println(sbBuffer.toString());
        return sbBuffer.toString();
    }

    /**
     * //产生5位长度的随机字符串，中文环境下是乱码
     RandomStringUtils.random(5);
     //使用指定的字符生成5位长度的随机字符串
     RandomStringUtils.random(5, new char[]{'a','b','c','d','e','f', '1', '2', '3'});
     //生成指定长度的字母和数字的随机组合字符串
     RandomStringUtils.randomAlphanumeric(5);
     //生成随机数字字符串
     RandomStringUtils.randomNumeric(5);
     //生成随机[a-z]字符串，包含大小写
     RandomStringUtils.randomAlphabetic(5);
     //生成从ASCII 32到126组成的随机字符串
     RandomStringUtils.randomAscii(4)
     * @param count
     * @return
     */

    public static String getRandomStringByLength(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            initKey();
            System.out.println(getRandomStringByLength(32).toCharArray());
        }

    }



}
