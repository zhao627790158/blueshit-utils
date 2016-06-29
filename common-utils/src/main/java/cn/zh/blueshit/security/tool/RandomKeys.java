package cn.zh.blueshit.security.tool;

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

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            initKey();
        }

    }

}
