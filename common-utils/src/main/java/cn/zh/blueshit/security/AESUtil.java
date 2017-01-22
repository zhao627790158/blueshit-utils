package cn.zh.blueshit.security;

import cn.zh.blueshit.security.tool.StringToByteTools;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESUtil {

    /**
     * 　　* 加密
     * <p>
     * 　　*
     * <p>
     * 　　* @param content 需要加密的内容
     * <p>
     * 　　* @param password 加密密码
     * <p>
     * 　　* @return
     * <p>
     */

    public static byte[] encrypt(String content, String password) {

        try {

            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);

            //3.产生原始对称密钥
            SecretKey secretKey = kgen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] enCodeFormat = secretKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器

            byte[] byteContent = content.getBytes("utf-8");

            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化

            byte[] result = cipher.doFinal(byteContent);

            return result; // 加密

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    /**
     * 解密
     * <p>
     * 　　* @param content 待解密内容
     * <p>
     * 　　* @param password 解密密钥
     * <p>
     * 　　* @return
     * <p>
     */

    public static byte[] decrypt(byte[] content, String password) {

        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            //3.产生原始对称密钥
            SecretKey secretKey = kgen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] enCodeFormat = secretKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            //6.根据指定算法AES生成密码器
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化

            byte[] result = cipher.doFinal(content);

            return result; // 加密

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    public static void main(String[] args) {
        String content = "加密前";

        String password = "d48a29980d1970470009a7f011256f2f";

        // 加密

        System.out.println("加密前：" + content);

        byte[] encryptResult = encrypt(content, password);

        String encryptResultStr = StringToByteTools.parseByte2HexStr(encryptResult);

        System.out.println("加密后：" + encryptResultStr);

        // 解密

        byte[] decryptFrom = StringToByteTools.parseHexStr2Byte(encryptResultStr);

        byte[] decryptResult = decrypt(decryptFrom, password);

        System.out.println("解密后：" + new String(decryptResult));

    }
}
