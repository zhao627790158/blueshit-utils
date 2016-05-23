package cn.zh.blueshit.security;

import cn.zh.blueshit.security.tool.StringToByteTools;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by zhaoheng on 2016/5/20.
 */
public class AESCoder {
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALOGORITHM = "AES/ECB/PKCS5Padding";

    public AESCoder() {
    }

    private static Key toKey(byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }
    public static String encrypt(String data, String key, String encode) throws Exception {
        Key k = toKey(key.getBytes());
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, k);
        return StringToByteTools.parseByte2HexStr(cipher.doFinal(data.getBytes(encode)));
    }

    public static String decrypt(String data, String key, String encode) throws Exception {
        Key k = toKey(key.getBytes());
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, k);
        return new String(cipher.doFinal(StringToByteTools.parseHexStr2Byte(data)), encode);
    }

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        String key = "!@#$%^&*(!@#$%^&";
        String keyLen = StringToByteTools.parseByte2HexStr(key.getBytes());
        System.out.println(key.getBytes());
        System.out.println(keyLen);
        String data = "37052319860208033XXX8";
        String data2 = "dfafdsaf";
        System.out.println("org:" + data);
        try {
            System.out.println(data.length());
            String t2 = encrypt(data, key, "utf-8");
            System.out.println(t2);
            System.out.println(t2.length());
            String dencodeR = decrypt(t2, key, "utf-8");
            System.out.println(dencodeR);
        } catch (Exception var12) {
            var12.printStackTrace();
        }

        long t21 = System.currentTimeMillis();
        System.out.println(t21 - t1);

        try {
            String t3 = encrypt(data2, key, "utf-8");
            String dencodeR2 = decrypt(t3, key, "utf-8");
            System.out.println(dencodeR2);
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        long t31 = System.currentTimeMillis();
        System.out.println(t31 - t21);
    }


}
